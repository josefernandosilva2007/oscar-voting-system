package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
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
    @PatchMapping("/movies/{id}")
    public MovieModel updateMovie(@PathVariable UUID id, @RequestBody @Valid MovieDTO dto) {
        return movieService.updateMovie(id, dto);
    }
    @DeleteMapping("/movies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable UUID id) { movieService.deleteMovie(id); }
}
