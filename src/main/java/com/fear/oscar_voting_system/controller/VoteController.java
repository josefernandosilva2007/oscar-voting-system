package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.ResponseUserVoteDTO;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.model.VoteModel;
import com.fear.oscar_voting_system.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "*")
public class VoteController {
    @Autowired
    VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteModel> saveVote(@RequestBody @Valid VoteDTO data, Authentication auth){
        requireOwner(data.userId(), auth);
        VoteModel vote = voteService.saveVote(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }
    @GetMapping
    public ResponseEntity<List<VoteModel>> listAllVotes(){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.showAllVotes());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResponseUserVoteDTO>> listVotesByUser(@PathVariable UUID userId, Authentication auth) {
        requireOwner(userId, auth);
        List<ResponseUserVoteDTO> votes = voteService.listVotesByUser(userId);
        return ResponseEntity.ok(votes);
    }

    @PutMapping("/{voteId}")
    public ResponseEntity<VoteModel> updateVote(@PathVariable UUID voteId, @RequestBody @Valid VoteDTO data, Authentication auth) {
        requireOwner(data.userId(), auth);
        return ResponseEntity.ok(voteService.updateVote(voteId, data));
    }

    private void requireOwner(UUID userId, Authentication auth) {
        if (!auth.getName().equals(userId.toString()) && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
            throw new AccessDeniedException("Ação permitida apenas para o próprio participante");
    }
}
