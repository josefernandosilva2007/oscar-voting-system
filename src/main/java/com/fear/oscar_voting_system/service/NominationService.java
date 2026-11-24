package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.NominationDTO;
import com.fear.oscar_voting_system.dto.CeremonyProgressDTO;
import com.fear.oscar_voting_system.model.NominationModel;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Objects;
import com.fear.oscar_voting_system.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
@Service
@RequiredArgsConstructor
public class NominationService {

    private final NominationRepository nominationRepository;
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    private final Environment environment;
    private final VoteRepository votes;
    @Value("${oscar.voting.deadline:9999-12-31T23:59:59}")
    private LocalDateTime votingDeadline;

    public CeremonyProgressDTO progress(int year) {
        String startsAt = environment.getProperty("oscar.ceremony." + year + ".starts-at");
        boolean ceremonyStarted = startsAt != null && !startsAt.isBlank()
            && !Instant.now().isBefore(OffsetDateTime.parse(startsAt).toInstant());
        return new CeremonyProgressDTO(year, nominationRepository.countDistinctByYear(year),
            nominationRepository.countDistinctByYearAndIsWinnerIsNotNull(year),
            LocalDateTime.now().isBefore(votingDeadline), ceremonyStarted);
    }

    public List<NominationModel> list() { return nominationRepository.findAll(); }


    public NominationModel create(NominationDTO dto) {
        return apply(new NominationModel(), dto);
    }

    @Transactional
    public NominationModel update(UUID id, NominationDTO dto) {
        NominationModel current = nominationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Indicação não encontrada"));
        boolean identityChanged = !Objects.equals(current.getCategory().getId(), dto.categoryId())
            || !Objects.equals(current.getMovie().getId(), dto.movieId())
            || !Objects.equals(current.getPerson().getId(), dto.personId()) || current.getYear() != dto.year();
        if (identityChanged && (current.getIsWinner() != null || votes.existsByNomination_Id(id)))
            throw new BusinessException("Esta indicação já tem palpites ou resultado. Só a imagem e o status podem ser alterados.");
        return apply(current, dto);
    }

    @Transactional
    public void delete(UUID id) {
        NominationModel current = nominationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Indicação não encontrada"));
        if (current.getIsWinner() != null || votes.existsByNomination_Id(id))
            throw new BusinessException("Esta indicação já tem palpites ou resultado e não pode ser apagada.");
        nominationRepository.delete(current);
    }

    private NominationModel apply(NominationModel nomination, NominationDTO dto) {
        nomination.setCategory(categoryRepository.findById(dto.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada")));
        nomination.setMovie(movieRepository.findById(dto.movieId())
            .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado")));
        nomination.setPerson(personRepository.findById(dto.personId())
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada")));
        nomination.setYear(dto.year());
        nomination.setStatus(dto.status());
        nomination.setArtworkSource(dto.artworkSource());
        return nominationRepository.save(nomination);
    }
}
