package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    public MovieModel saveMovie(MovieDTO dto) {
        return applyMovie(new MovieModel(), dto);
    }
    public MovieModel updateMovie(UUID id, MovieDTO dto) {
        return applyMovie(movieRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado")), dto);
    }
    private MovieModel applyMovie(MovieModel movie, MovieDTO dto) {
        PersonModel person = dto.personId() == null ? null : personRepository.findById(dto.personId())
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        movie.setName(dto.name());
        movie.setPerson(person);
        movie.setSynopsis(dto.synopsis());
        movie.setImageUrl(dto.imageUrl());
        return movieRepository.save(movie);
    }
    public void deleteMovie(UUID id) { movieRepository.deleteById(id); }
    public List<MovieModel> listAllMovies() { return movieRepository.findAll(); }
}
