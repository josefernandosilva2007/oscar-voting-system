package com.fear.oscar_voting_system.controller;


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

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDTO));
    }
    @PostMapping("/users/login")
    public ResponseEntity<UserModel> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        UserModel user = userService.executeLogin(username, password);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> listAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.listAllUsers());
    }
}