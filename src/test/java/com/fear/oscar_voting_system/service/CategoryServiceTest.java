package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.TestFixtures;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock CategoryRepository categories;
    @Mock NominationRepository nominations;
    @InjectMocks CategoryService service;

    @Test void replacesWinnerWithinCategoryAndYear() {
        UUID categoryId = UUID.randomUUID(), winnerId = UUID.randomUUID();
        var category = TestFixtures.category().id(categoryId).build();
        var winner = TestFixtures.nomination().id(winnerId).category(category).year(2026).movie(TestFixtures.movie().name("Filme").build())
                .person(TestFixtures.person()).status(1).build();
        var previous = TestFixtures.nomination().id(UUID.randomUUID()).category(category)
                .year(2026).isWinner(true).movie(TestFixtures.movie().name("Filme").build())
                .person(TestFixtures.person()).status(1).build();
        when(categories.findById(categoryId)).thenReturn(Optional.of(category));
        when(nominations.findById(winnerId)).thenReturn(Optional.of(winner));
        when(nominations.findByCategory_IdAndYear(categoryId, 2026)).thenReturn(List.of(winner, previous));
        service.defineWinningNomination(categoryId, winnerId);
        assertEquals(Boolean.TRUE, winner.getIsWinner());
        assertEquals(Boolean.FALSE, previous.getIsWinner());
        verify(nominations).findByCategory_IdAndYear(categoryId, 2026);
    }
    @Test void rejectsNominationFromAnotherCategory() {
        UUID categoryId = UUID.randomUUID(), nominationId = UUID.randomUUID();
        when(categories.findById(categoryId)).thenReturn(Optional.of(TestFixtures.category().id(categoryId).build()));
        var nomination = TestFixtures.nomination().category(TestFixtures.category().id(UUID.randomUUID()).build()).build();
        when(nominations.findById(nominationId)).thenReturn(Optional.of(nomination));
        assertThrows(BusinessException.class, () -> service.defineWinningNomination(categoryId, nominationId));
        assertNull(nomination.getIsWinner());
        verify(nominations, never()).findByCategory_IdAndYear(any(), anyInt());
    }
    @Test void rejectsMissingCategory() {
        UUID id = UUID.randomUUID();
        when(categories.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.defineWinningNomination(id, UUID.randomUUID()));
        verifyNoInteractions(nominations);
    }
    @Test void rejectsMissingNomination() {
        UUID id = UUID.randomUUID(), nominationId = UUID.randomUUID();
        when(categories.findById(id)).thenReturn(Optional.of(TestFixtures.category().id(id).build()));
        when(nominations.findById(nominationId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.defineWinningNomination(id, nominationId));
    }
}
