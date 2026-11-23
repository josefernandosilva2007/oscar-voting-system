package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.NominationDTO;
import com.fear.oscar_voting_system.dto.CeremonyProgressDTO;
import com.fear.oscar_voting_system.model.NominationModel;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
@Service
@RequiredArgsConstructor
public class NominationService {

    private final NominationRepository nominationRepository;
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    @Value("${oscar.voting.deadline:9999-12-31T23:59:59}")
    private LocalDateTime votingDeadline;

    public CeremonyProgressDTO progress(int year) {
        return new CeremonyProgressDTO(year, nominationRepository.countDistinctByYear(year),
            nominationRepository.countDistinctByYearAndIsWinnerIsNotNull(year),
            LocalDateTime.now().isBefore(votingDeadline));
    }

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
