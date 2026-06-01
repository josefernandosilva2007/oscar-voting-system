package com.fear.oscar_voting_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "nomination")
public class NominationModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @NotNull
    private MovieModel movie;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @NotNull
    private PersonModel person;

    private int year;



    private Boolean isWinner;
}
