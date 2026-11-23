package com.fear.oscar_voting_system.dto;

import java.util.UUID;

public record ShareCardDTO(UUID userId, String username, String imageUrl, int year,
                           int rank, int points, long predictions, long correctPredictions) {}
