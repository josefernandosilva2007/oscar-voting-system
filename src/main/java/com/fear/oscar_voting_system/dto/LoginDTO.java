package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.NotBlank;
public record LoginDTO(@NotBlank String username, @NotBlank String password) {}
