package com.fear.oscar_voting_system.dto;

import java.util.UUID;

public record ResponseUserVoteDTO(
        UUID id,
        UUID categoryId,
        String categoryName,
        String movieName,
        String movieImageUrl,
        boolean isWinner
) {
}
