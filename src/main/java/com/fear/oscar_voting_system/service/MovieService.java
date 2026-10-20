package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    public MovieModel saveMovie(MovieDTO dto) {
        PersonModel person = dto.personId() == null ? null : personRepository.findById(dto.personId())
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        return movieRepository.save(MovieModel.builder().name(dto.name()).person(person)
            .synopsis(dto.synopsis()).build());
    }
    public List<MovieModel> listAllMovies() { return movieRepository.findAll(); }
}
