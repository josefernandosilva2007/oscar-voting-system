package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record MovieDTO(@NotBlank @Size(max = 200) String name, UUID personId, String synopsis,
                       @Size(max = 2048) String imageUrl) {
    public MovieDTO(String name, UUID personId, String synopsis) {
        this(name, personId, synopsis, null);
    }
}
