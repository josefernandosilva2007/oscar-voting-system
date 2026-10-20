package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.TestFixtures;
import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.PersonModel;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock MovieRepository movies;
    @Mock PersonRepository people;
    @InjectMocks MovieService service;

    @Test void allowsMovieWithoutPerson() {
        when(movies.save(any())).thenAnswer(i -> i.getArgument(0));
        var result = service.saveMovie(new MovieDTO("Filme", null, "Sinopse"));
        assertEquals("Filme", result.getName());
        assertEquals("Sinopse", result.getSynopsis());
        assertNull(result.getPerson());
        verifyNoInteractions(people);
    }
    @Test void associatesExistingPerson() {
        UUID id = UUID.randomUUID();
        var person = TestFixtures.person();
        person.setId(id);
        when(people.findById(id)).thenReturn(Optional.of(person));
        when(movies.save(any())).thenAnswer(i -> i.getArgument(0));
        assertSame(person, service.saveMovie(new MovieDTO("Filme", id, null)).getPerson());
    }
    @Test void rejectsUnknownPersonWithoutSavingMovie() {
        UUID id = UUID.randomUUID();
        when(people.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.saveMovie(new MovieDTO("Filme", id, null)));
        verifyNoInteractions(movies);
    }
}
