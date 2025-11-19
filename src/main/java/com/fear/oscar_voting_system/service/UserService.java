package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserModel saveUser(UserDTO userDTO){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        return userRepository.save(userModel);
    }


    public List<UserModel> listAllUsers(){
        return userRepository.findAll();
    }
}