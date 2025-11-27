package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.model.VoteModel;
import com.fear.oscar_voting_system.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {
    @Autowired
    VoteService voteService;

    @PostMapping("/votes")
    public ResponseEntity<VoteModel> saveVote(@RequestBody @Valid VoteDTO data){
        VoteModel vote = voteService.saveVote(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }
    @GetMapping("/votes")
    public ResponseEntity<List<VoteModel>> listAllVotes(){
        return ResponseEntity.status(HttpStatus.OK).body(voteService.showAllVotes());
    }
}