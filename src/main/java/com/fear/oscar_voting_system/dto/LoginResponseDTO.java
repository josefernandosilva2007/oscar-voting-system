package com.fear.oscar_voting_system.dto;

import java.util.UUID;

public record LoginResponseDTO(UUID userId, String username, String role, String accessToken) {}
