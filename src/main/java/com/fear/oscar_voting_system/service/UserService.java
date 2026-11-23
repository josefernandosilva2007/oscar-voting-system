package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.fear.oscar_voting_system.security.TokenService;
import com.fear.oscar_voting_system.dto.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    @Value("${oscar.auth.admin-username:}")
    private String adminUsername = "";
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this(userRepository, passwordEncoder,
            new TokenService("local-development-secret-change-before-deploy-32chars"));
    }
    public UserModel saveUser(UserDTO dto) {
        if (!adminUsername.isBlank() && adminUsername.equalsIgnoreCase(dto.username()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de usuário reservado");
        return userRepository.save(UserModel.builder().username(dto.username()).email(dto.email())
            .passwordHashed(passwordEncoder.encode(dto.password())).build());
    }
    public LoginResponseDTO login(String username, String password) {
        UserModel user = executeLogin(username, password);
        String role = !adminUsername.isBlank() && adminUsername.equalsIgnoreCase(user.getUsername()) ? "ADMIN" : "USER";
        return new LoginResponseDTO(user.getId(), user.getUsername(), role, tokenService.issue(user.getId(), role));
    }
    public UserModel executeLogin(String username, String password) {
        UserModel user = userRepository.findByUsername(username)
            .filter(u -> u.getDeletedAt() == null)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login ou senha inválidos"));
        if (!passwordEncoder.matches(password, user.getPasswordHashed()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login ou senha inválidos");
        return user;
    }
    public List<UserModel> listAllUsers() { return userRepository.findAll(); }
    @Transactional
    public void updateProfilePicture(UUID id, String url) {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        user.setImageUrl(url);
    }
}
