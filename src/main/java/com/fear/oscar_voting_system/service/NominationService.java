package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.NominationDTO;
import com.fear.oscar_voting_system.model.NominationModel;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class NominationService {

    private final NominationRepository nominationRepository;
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;

    public List<NominationModel> list() { return nominationRepository.findAll(); }


    public NominationModel create(NominationDTO dto) {
        return nominationRepository.save(NominationModel.builder()
            .category(categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada")))
            .movie(movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado")))
            .person(personRepository.findById(dto.personId())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada")))
            .year(dto.year()).status(dto.status()).build());
    }
}
