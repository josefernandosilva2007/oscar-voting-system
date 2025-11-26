package com.fear.oscar_voting_system.dto;


import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.model.UserModel;

import java.util.UUID;

public record VoteDTO(UUID userId, UUID movieId, UUID categoryId){

}