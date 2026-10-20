package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.TestFixtures;
import com.fear.oscar_voting_system.dto.NominationDTO;
import com.fear.oscar_voting_system.model.*;
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
class NominationServiceTest {
    @Mock NominationRepository nominations;
    @Mock CategoryRepository categories;
    @Mock MovieRepository movies;
    @Mock PersonRepository people;
    @InjectMocks NominationService service;
    final UUID categoryId = UUID.randomUUID(), movieId = UUID.randomUUID(), personId = UUID.randomUUID();
    final NominationDTO dto = new NominationDTO(categoryId, movieId, personId, 2026, 1);

    @Test void createsPendingNominationWithResolvedReferences() {
        var category = TestFixtures.category().id(categoryId).build();
        var movie = TestFixtures.movie().id(movieId).build();
        var person = TestFixtures.person();
        person.setId(personId);
        when(categories.findById(categoryId)).thenReturn(Optional.of(category));
        when(movies.findById(movieId)).thenReturn(Optional.of(movie));
        when(people.findById(personId)).thenReturn(Optional.of(person));
        when(nominations.save(any())).thenAnswer(i -> i.getArgument(0));
        var result = service.create(dto);
        assertSame(category, result.getCategory());
        assertSame(movie, result.getMovie());
        assertSame(person, result.getPerson());
        assertEquals(2026, result.getYear());
        assertEquals(Integer.valueOf(1), result.getStatus());
        assertNull(result.getIsWinner());
    }
    @Test void rejectsMissingCategory() {
        when(categories.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
        verifyNoInteractions(nominations, movies, people);
    }
    @Test void rejectsMissingMovie() {
        when(categories.findById(categoryId)).thenReturn(Optional.of(TestFixtures.category().build()));
        when(movies.findById(movieId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
        verifyNoInteractions(nominations, people);
    }
    @Test void rejectsMissingPerson() {
        when(categories.findById(categoryId)).thenReturn(Optional.of(TestFixtures.category().build()));
        when(movies.findById(movieId)).thenReturn(Optional.of(TestFixtures.movie().build()));
        when(people.findById(personId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
        verifyNoInteractions(nominations);
    }
}
