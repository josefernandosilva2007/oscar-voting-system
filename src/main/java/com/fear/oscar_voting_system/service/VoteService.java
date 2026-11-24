package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserVoteDTO;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.exception.BusinessException;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NominationRepository nominationRepository;

    @Value("${oscar.voting.deadline:9999-12-31T23:59:59}")
    private LocalDateTime votingDeadline = LocalDateTime.of(9999, 12, 31, 23, 59, 59);


    @Transactional
    public VoteModel saveVote(VoteDTO voteDTO) {
        ensureVotingOpen();
        UserModel user =
                userRepository.findById(voteDTO.userId())
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
        NominationModel nomination = openNomination(voteDTO.nominationId());

        CategoryModel category = nomination.getCategory();

        if (voteRepository.existsByUser_IdAndCategory_IdAndNomination_Year(user.getId(), category.getId(), nomination.getYear()))
            throw new BusinessException("Você já votou nesta categoria!");

        VoteModel vote = VoteModel
                .builder()
                .user(user)
                .nomination(nomination)
                .category(category)
                .year(nomination.getYear())
                .build();

        return voteRepository.save(vote);
    }

    @Transactional
    public VoteModel updateVote(UUID voteId, VoteDTO voteDTO) {
        ensureVotingOpen();
        VoteModel vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Palpite não encontrado"));
        if (!vote.getUser().getId().equals(voteDTO.userId()))
            throw new BusinessException("Este palpite pertence a outro participante");
        NominationModel nomination = openNomination(voteDTO.nominationId());
        if (!nomination.getCategory().getId().equals(vote.getCategory().getId()) || nomination.getYear() != vote.getYear())
            throw new BusinessException("A indicação deve pertencer à mesma categoria e edição");
        vote.setNomination(nomination);
        return voteRepository.save(vote);
    }

    private NominationModel openNomination(UUID id) {
        NominationModel nomination = nominationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Indicação não encontrada"));
        if (nominationRepository.existsByCategory_IdAndYearAndIsWinnerTrue(
                nomination.getCategory().getId(), nomination.getYear()))
            throw new BusinessException("A votação desta categoria encerrou porque o vencedor já foi publicado.");
        if (Integer.valueOf(0).equals(nomination.getStatus()))
            throw new BusinessException("Esta indicação está fechada para palpites");
        return nomination;
    }

    private void ensureVotingOpen() {
        if (!LocalDateTime.now().isBefore(votingDeadline))
            throw new BusinessException("O prazo para palpites terminou");
    }

    @Transactional(readOnly = true)
    public List<ResponseUserVoteDTO> listVotesByUserAndYear(UUID userId, int year) {
        return voteRepository.findByUser_IdAndYear(userId, year).stream().map(this::voteDetails).toList();
    }

    @Transactional(readOnly = true)
    public List<ResponseUserVoteDTO> listVotesByUser(UUID userId) {
        return voteRepository.findByUser_Id(userId).stream().map(this::voteDetails).toList();
    }

    private ResponseUserVoteDTO voteDetails(VoteModel v) {
            NominationModel n = v.getNomination();
            return new ResponseUserVoteDTO(v.getId(), v.getCategory().getId(),
                v.getCategory().getName(), n.getId(), n.getMovie().getName(),
                n.getPerson().getName(), n.getYear(), n.getIsWinner());
    }

    @Transactional(readOnly = true)
    public List<VoteModel> showAllVotes() {
        if (LocalDateTime.now().isBefore(votingDeadline))
            throw new BusinessException("Os palpites serão revelados após o encerramento das votações");
        return voteRepository.findAll();
    }


}
