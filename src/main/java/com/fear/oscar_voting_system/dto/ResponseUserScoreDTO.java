package com.fear.oscar_voting_system.dto;

import java.util.UUID;

public record ResponseUserScoreDTO(UUID id, String username, int score,String profilePictureUrl) {
}
