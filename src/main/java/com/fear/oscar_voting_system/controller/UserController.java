package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.UpdateProfilePictureDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.dto.LoginResponseDTO;
import com.fear.oscar_voting_system.dto.ShareCardDTO;
import com.fear.oscar_voting_system.service.ShareCardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ShareCardService shareCardService;
    @Autowired
    com.fear.oscar_voting_system.service.VoteService voteService;

    public record ParticipantProfile(ShareCardDTO profile,
            List<com.fear.oscar_voting_system.dto.ResponseUserVoteDTO> votes) {}

    @GetMapping("/{id}/profile/{year}")
    public ParticipantProfile participantProfile(@PathVariable UUID id, @PathVariable int year) {
        return new ParticipantProfile(shareCardService.getCard(id, year), voteService.revealedVotes(id, year));
    }

    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid com.fear.oscar_voting_system.dto.LoginDTO credentials) {
        return ResponseEntity.ok(userService.login(credentials.username(), credentials.password()));
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> listAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.listAllUsers());
    }

    @PatchMapping("/{id}/picture")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable UUID id, @RequestBody @Valid UpdateProfilePictureDTO dto, Authentication auth){
        if (!auth.getName().equals(id.toString()) && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
            throw new AccessDeniedException("Ação permitida apenas para o próprio participante");
        userService.updateProfilePicture(id, dto.pictureUrl());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/share-card/{year}")
    public ShareCardDTO shareCard(@PathVariable UUID id, @PathVariable int year, Authentication auth) {
        if (!auth.getName().equals(id.toString()) && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
            throw new AccessDeniedException("Ação permitida apenas para o próprio participante");
        return shareCardService.getCard(id, year);
    }
}
