package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserVoteDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.dto.VoteDTO;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.model.VoteModel;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.repository.VoteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserModel saveUser(UserDTO userDTO){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        return userRepository.save(userModel);
    }
    public UserModel executeLogin(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Login ou senha inválidos"));
    }

    public List<UserModel> listAllUsers(){
        return userRepository.findAll();
    }

    public void updateProfilePicture(UUID id, String profilePictureUrl) {
        if(!userRepository.existsById(id)) throw new RuntimeException("esse usuario nao existe");
        userRepository.updateProfilePicture(id, profilePictureUrl);


    }

}