package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public void saveMovie(MovieDTO movieDTO){
        MovieModel model = MovieModel.builder()
                .name(movieDTO.name())
                .person(movieDTO.person())
                .synopsis(movieDTO.synopsis())
                .imageUrl(movieDTO.imageUrl())
                .build();

        movieRepository.save(model);
    }


    public List<MovieModel> listAllMovies(){return movieRepository.findAll();}







}