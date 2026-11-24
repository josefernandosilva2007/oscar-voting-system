package com.fear.oscar_voting_system.config;

import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("local")
class LocalAdminConfig {
    @Bean
    ApplicationRunner createLocalAdmin(UserRepository users, PasswordEncoder encoder,
            @Value("${oscar.auth.admin-username}") String username,
            @Value("${OCG_LOCAL_ADMIN_PASSWORD}") String password) {
        return args -> {
            if (users.findByUsername(username).isEmpty()) {
                users.save(UserModel.builder().username(username).email("admin-local@example.invalid")
                        .passwordHashed(encoder.encode(password)).build());
            }
        };
    }
}
