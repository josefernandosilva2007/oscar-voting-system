package com.fear.oscar_voting_system.dto;


import java.util.UUID;

public record VoteDTO(@jakarta.validation.constraints.NotNull UUID userId, @jakarta.validation.constraints.NotNull UUID nominationId){

}