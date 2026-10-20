package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.TestFixtures;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {
    @Mock VoteRepository votes;
    @Mock UserRepository users;
    @Mock NominationRepository nominations;
    @InjectMocks VoteService service;

    @Test void derivesVoteCategoryFromNominationAndAllowsNewYear() {
        UUID userId = UUID.randomUUID(), nominationId = UUID.randomUUID(), categoryId = UUID.randomUUID();
        var user = TestFixtures.user().id(userId).build();
        var category = TestFixtures.category().id(categoryId).build();
        var nomination = TestFixtures.nomination().id(nominationId).category(category).year(2027).build();
        when(users.findById(userId)).thenReturn(Optional.of(user));
        when(nominations.findById(nominationId)).thenReturn(Optional.of(nomination));
        when(votes.existsByUser_IdAndCategory_IdAndNomination_Year(userId, categoryId, 2027)).thenReturn(false);
        when(votes.save(any())).thenAnswer(i -> i.getArgument(0));
        var result = service.saveVote(new VoteDTO(userId, nominationId));
        assertSame(category, result.getCategory());
        assertSame(nomination, result.getNomination());
        assertSame(user, result.getUser());
    }
    @Test void rejectsMissingUserBeforeSaving() {
        UUID id = UUID.randomUUID();
        when(users.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.saveVote(new VoteDTO(id, UUID.randomUUID())));
        verifyNoInteractions(votes, nominations);
    }
    @Test void rejectsMissingNominationBeforeSaving() {
        UUID id = UUID.randomUUID(), nominationId = UUID.randomUUID();
        when(users.findById(id)).thenReturn(Optional.of(TestFixtures.user().id(id).build()));
        when(nominations.findById(nominationId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.saveVote(new VoteDTO(id, nominationId)));
        verifyNoInteractions(votes);
    }
    @Test void preservesPendingResultInVoteResponse() {
        UUID userId = UUID.randomUUID(), voteId = UUID.randomUUID(), nominationId = UUID.randomUUID();
        var category = TestFixtures.category().id(UUID.randomUUID()).name("Ator").build();
        var person = TestFixtures.person();
        person.setName("Pessoa");
        var nomination = TestFixtures.nomination().id(nominationId).category(category)
            .movie(TestFixtures.movie().name("Filme").build()).person(person).year(2026).build();
        when(votes.findByUser_Id(userId)).thenReturn(List.of(TestFixtures.vote()
            .id(voteId).category(category).nomination(nomination).build()));
        var result = service.listVotesByUser(userId).getFirst();
        assertEquals(voteId, result.id());
        assertEquals(category.getId(), result.categoryId());
        assertEquals("Ator", result.categoryName());
        assertEquals(nominationId, result.nominationId());
        assertEquals("Filme", result.movieName());
        assertEquals("Pessoa", result.personName());
        assertEquals(2026, result.year());
        assertNull(result.isWinner());
    }
}
