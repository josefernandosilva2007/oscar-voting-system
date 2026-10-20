package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.TestFixtures;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository users;
    @Mock PasswordEncoder encoder;
    @InjectMocks UserService service;

    @Test void acceptsValidCredentials() {
        var user = TestFixtures.user().username("ana").passwordHashed("hash").build();
        when(users.findByUsername("ana")).thenReturn(Optional.of(user));
        when(encoder.matches("password", "hash")).thenReturn(true);
        assertSame(user, service.executeLogin("ana", "password"));
    }
    @Test void rejectsWrongPassword() {
        when(users.findByUsername("ana")).thenReturn(Optional.of(
            TestFixtures.user().passwordHashed("hash").build()));
        var error = assertThrows(ResponseStatusException.class,
            () -> service.executeLogin("ana", "wrong"));
        assertEquals(401, error.getStatusCode().value());
    }
    @Test void rejectsUnknownUser() {
        when(users.findByUsername("unknown")).thenReturn(Optional.empty());
        var error = assertThrows(ResponseStatusException.class,
            () -> service.executeLogin("unknown", "password"));
        assertEquals(401, error.getStatusCode().value());
        verifyNoInteractions(encoder);
    }
    @Test void rejectsDeletedUserEvenWithValidPassword() {
        when(users.findByUsername("ana")).thenReturn(Optional.of(
            TestFixtures.user().passwordHashed("hash").deletedAt(LocalDateTime.now()).build()));
        var error = assertThrows(ResponseStatusException.class,
            () -> service.executeLogin("ana", "password"));
        assertEquals(401, error.getStatusCode().value());
        verifyNoInteractions(encoder);
    }
    @Test void updatesImageUrlOfExistingUser() {
        UUID id = UUID.randomUUID();
        var user = TestFixtures.user().id(id).build();
        when(users.findById(id)).thenReturn(Optional.of(user));
        service.updateProfilePicture(id, "https://example.com/photo.png");
        assertEquals("https://example.com/photo.png", user.getImageUrl());
    }
    @Test void rejectsPictureUpdateForMissingUser() {
        UUID id = UUID.randomUUID();
        when(users.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateProfilePicture(id, "url"));
    }
}
