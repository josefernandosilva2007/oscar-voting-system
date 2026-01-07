package com.fear.oscar_voting_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Category")
@Data
public class CategoryModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<MovieModel> movies;

    @ManyToOne
    @JoinColumn(name = "winning_movie_id")
    private MovieModel movieWinning;
}