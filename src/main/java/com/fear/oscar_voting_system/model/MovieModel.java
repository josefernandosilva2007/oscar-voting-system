package com.fear.oscar_voting_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Movie")
@Data
public class MovieModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String director;
    private String imageUrl;


    @ManyToMany
    @JsonIgnore
    private List<CategoryModel> categories;
}