package com.fear.oscar_voting_system.dto;

import java.util.UUID;

public record ResponseUserVoteDTO(
        UUID id,
        String categoryName,
        String movieName,
        UUID categoryId,
        boolean isWinner
) {
}
