package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserVoteDTO;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.exception.BusinessException;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.model.VoteModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.MovieRepository;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.repository.VoteRepository;
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
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;


    public VoteModel saveVote(VoteDTO voteDTO) {
        UserModel user =
                userRepository.findById(voteDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
        MovieModel movie =
                movieRepository.findById(voteDTO.movieId())
                .orElseThrow(() -> new ResourceNotFoundException("Filme nao Encontrado"));
        CategoryModel category =
                categoryRepository.findById(voteDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao Encontrado"));


        boolean categoryFound =
                movie.getCategories()
                .stream()
                .anyMatch(c -> c.getId().equals(category.getId()));

        if (!categoryFound)
            throw new RuntimeException("Este filme nao concorre na categoria");

        if (voteRepository.existsByUser_IdAndCategory_Id(user.getId(),category.getId()))
            throw new BusinessException("Você já votou nesta categoria!");

        VoteModel vote = VoteModel
                .builder()
                .user(user)
                .movie(movie)
                .category(category)
                .build();

        return voteRepository.save(vote);
    }

    @Transactional(readOnly = true)
    public List<ResponseUserVoteDTO> listVotesByUser(UUID userId) {
        return voteRepository.findByUser_Id(userId).stream()
                .map(vote -> {
                   Boolean isWinner = Optional.ofNullable(vote.getCategory().getMovieWinning())
                           .map(winner -> winner.getId().equals(vote.getMovie().getId()))
                           .orElse(false);

                   return new ResponseUserVoteDTO(
                           vote.getId(),
                           vote.getCategory().getId(),
                           vote.getCategory().getName(),
                           vote.getMovie().getName(),
                           vote.getMovie().getImageUrl(),
                           isWinner
                   );
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VoteModel> showAllVotes(){
        return voteRepository.findAll();
    }


}