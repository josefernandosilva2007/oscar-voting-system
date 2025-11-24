package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.service.CategoryService;
import com.fear.oscar_voting_system.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<List<MovieModel>> listAllMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.listAllMovies());
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieModel> saveMovie(@RequestBody @Valid MovieDTO movieDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.saveMovie(movieDTO));
    }
}