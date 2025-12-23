package com.fear.oscar_voting_system.dto;


import java.util.List;
import java.util.UUID;

public record MovieDTO(String name, String director,String imageUrl, List<UUID> categoriesID){
}