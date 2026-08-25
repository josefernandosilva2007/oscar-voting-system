package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserVoteDTO;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.exception.BusinessException;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        if (voteRepository.existsByUser_IdAndCategory_Id(user.getId(), category.getId()))
            throw new BusinessException("Você já votou nesta categoria!");

        VoteModel vote = VoteModel
                .builder()
                .user(user)
                .nomination(nomination)
                .category(category)
                .build();

        return voteRepository.save(vote);
    }

//    @Transactional(readOnly = true)
//    public List<ResponseUserVoteDTO> listVotesByUser(UUID userId) {
//        return voteRepository.findByUser_Id(userId).stream()
//                .map(vote -> {
//                    Boolean isWinner = Optional.ofNullable(vote.getCategory().getMovieWinning())
//                            .map(winner -> winner.getId().equals(vote.getMovie().getId()))
//                            .orElse(false);
//
//                    return new ResponseUserVoteDTO(
//                            vote.getId(),
//                            vote.getCategory().getId(),
//                            vote.getCategory().getName(),
//                            vote.getMovie().getName(),
//                            vote.getMovie().getImageUrl(),
//                            isWinner
//                    );
//                }).collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public List<VoteModel> showAllVotes() {
        return voteRepository.findAll();
    }


}