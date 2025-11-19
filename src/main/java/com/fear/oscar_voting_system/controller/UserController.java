package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDTO));
    }
}