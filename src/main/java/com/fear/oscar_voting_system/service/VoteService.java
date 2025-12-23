package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.model.VoteModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.MovieRepository;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public VoteModel saveVote(VoteDTO voteDTO) {
        UserModel user =
                userRepository.findById(voteDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        MovieModel movie =
                movieRepository.findById(voteDTO.movieId())
                .orElseThrow(() -> new RuntimeException("Filme nao Encontrado"));
        CategoryModel category =
                categoryRepository.findById(voteDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria nao Encontrado"));


        boolean categoryFound =
                movie.getCategories()
                .stream()
                .anyMatch(c -> c.getId().equals(category.getId()));
        if (!categoryFound)
            throw new RuntimeException("Este filme nao concorre na categoria");
        if (voteRepository.existsByUser_IdAndCategory_Id(user.getId(),category.getId()))
            throw new RuntimeException("Voce ja votou nesta categoria!");

        VoteModel vote = VoteModel
                .builder()
                .user(user)
                .movie(movie)
                .category(category)
                .build();

        return voteRepository.save(vote);
    }

    public List<VoteModel> showAllVotes(){
        return voteRepository.findAll();
    }
}