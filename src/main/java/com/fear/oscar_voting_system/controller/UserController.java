package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.UpdateProfilePictureDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        UserModel user = userService.executeLogin(username, password);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> listAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.listAllUsers());
    }

    @PatchMapping("/{id}/picture")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable UUID id, @RequestBody @Valid UpdateProfilePictureDTO dto){
        userService.updateProfilePicture(id, dto.pictureUrl());
        return ResponseEntity.noContent().build();
    }
}