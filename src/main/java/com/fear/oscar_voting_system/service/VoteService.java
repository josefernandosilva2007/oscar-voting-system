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

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NominationRepository nominationRepository;


    public VoteModel saveVote(VoteDTO voteDTO) {
        UserModel user =
                userRepository.findById(voteDTO.userId())
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
        NominationModel nomination =
                nominationRepository.findById(voteDTO.nominationId())
                        .orElseThrow(() -> new ResourceNotFoundException("Indicação não encontrada"));

        CategoryModel category = nomination.getCategory();

        if (voteRepository.existsByUser_IdAndCategory_IdAndNomination_Year(user.getId(), category.getId(), nomination.getYear()))
            throw new BusinessException("Você já votou nesta categoria!");

        VoteModel vote = VoteModel
                .builder()
                .user(user)
                .nomination(nomination)
                .category(category)
                .build();

        return voteRepository.save(vote);
    }

    @Transactional(readOnly = true)
    public List<ResponseUserVoteDTO> listVotesByUser(UUID userId) {
        return voteRepository.findByUser_Id(userId).stream().map(v -> {
            NominationModel n = v.getNomination();
            return new ResponseUserVoteDTO(v.getId(), v.getCategory().getId(),
                v.getCategory().getName(), n.getId(), n.getMovie().getName(),
                n.getPerson().getName(), n.getYear(), n.getIsWinner());
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<VoteModel> showAllVotes() {
        return voteRepository.findAll();
    }


}