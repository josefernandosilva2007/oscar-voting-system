package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.*;
public record UserDTO(@NotBlank @Size(max = 100) String username,
                      @NotBlank @Email @Size(max = 255) String email,
                      @NotBlank @Size(min = 8, max = 128) String password) {}
