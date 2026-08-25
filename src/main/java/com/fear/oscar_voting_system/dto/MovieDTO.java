package com.fear.oscar_voting_system.dto;


import com.fear.oscar_voting_system.model.PersonModel;

import java.util.List;
import java.util.UUID;

public record MovieDTO(String name, PersonModel person, String synopsis, String imageUrl){
}