package com.fear.oscar_voting_system.dto;


import java.util.UUID;

public record VoteDTO(UUID userId, UUID movieId, UUID categoryId){

}