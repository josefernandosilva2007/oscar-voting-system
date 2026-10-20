package com.fear.oscar_voting_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.exception.BusinessException;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.service.*;
import com.fear.oscar_voting_system.config.PasswordConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModelRefactorTests {
    @Test
    void registrationHashesPasswordAndKeepsEmail() {
        UserRepository users = mock(UserRepository.class);
        when(users.save(any())).thenAnswer(i -> i.getArgument(0));
        var encoder = new PasswordConfig().passwordEncoder();
        UserModel user = new UserService(users, encoder)
            .saveUser(new UserDTO("ana", "ana@example.com", "password123"));
        assertEquals("ana@example.com", user.getEmail());
        assertNotEquals("password123", user.getPasswordHashed());
        assertTrue(encoder.matches("password123", user.getPasswordHashed()));
    }

    @Test
    void userSerializationOmitsCredentialsAndVoteCycle() throws Exception {
        UserModel user = TestFixtures.user().username("ana").passwordHashed("secret").build();
        user.setVotes(List.of(TestFixtures.vote().user(user).build()));
        String json = new ObjectMapper().writeValueAsString(user);
        assertFalse(json.contains("secret"));
        assertFalse(json.contains("votes"));
    }

    @Test
    void duplicateVoteIsCheckedForNominationYear() {
        var votes = mock(VoteRepository.class);
        var users = mock(UserRepository.class);
        var nominations = mock(NominationRepository.class);
        var service = new VoteService();
        ReflectionTestUtils.setField(service, "voteRepository", votes);
        ReflectionTestUtils.setField(service, "userRepository", users);
        ReflectionTestUtils.setField(service, "nominationRepository", nominations);
        UUID userId = UUID.randomUUID(), categoryId = UUID.randomUUID(), nominationId = UUID.randomUUID();
        when(users.findById(userId)).thenReturn(Optional.of(TestFixtures.user().id(userId).build()));
        when(nominations.findById(nominationId)).thenReturn(Optional.of(TestFixtures.nomination()
            .id(nominationId).category(TestFixtures.category().id(categoryId).build()).year(2026).build()));
        when(votes.existsByUser_IdAndCategory_IdAndNomination_Year(userId, categoryId, 2026)).thenReturn(true);
        assertThrows(BusinessException.class, () -> service.saveVote(new VoteDTO(userId, nominationId)));
        verify(votes, never()).save(any());
    }
}
