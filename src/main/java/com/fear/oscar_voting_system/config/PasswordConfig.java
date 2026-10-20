package com.fear.oscar_voting_system.config;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;
@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder("", 16, 310000,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }
}
