package com.fear.oscar_voting_system.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenService tokens;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories", "/movies", "/people", "/nominations", "/nominations/ceremony/**", "/ranking/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/categories", "/movies", "/people", "/nominations").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/categories/*/winner").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/votes").hasRole("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(new BearerTokenFilter(tokens), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
