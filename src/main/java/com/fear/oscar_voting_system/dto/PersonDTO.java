package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.*;
public record PersonDTO(@NotBlank @Size(max = 100) String name, String imageUrl) {}
