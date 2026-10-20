package com.fear.oscar_voting_system.dto;
import java.util.UUID;
public record ResponseUserVoteDTO(UUID id, UUID categoryId, String categoryName,
                                  UUID nominationId, String movieName, String personName,
                                  int year, Boolean isWinner) {}
