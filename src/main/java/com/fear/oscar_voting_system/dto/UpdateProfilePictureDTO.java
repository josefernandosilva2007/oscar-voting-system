package com.fear.oscar_voting_system.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UpdateProfilePictureDTO(
        @NotBlank(message = "A URL da imagem nao pode ta vazia")
        String pictureUrl
) {}
