package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record MovieDTO(@NotBlank @Size(max = 200) String name, UUID personId, String synopsis) {}
