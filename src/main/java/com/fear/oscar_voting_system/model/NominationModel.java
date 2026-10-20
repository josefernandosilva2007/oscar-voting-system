package com.fear.oscar_voting_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nomination")
public class NominationModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @NotNull
    private MovieModel movie;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @NotNull
    private PersonModel person;

    private int year;



    @NotNull
    @Column(nullable = false)
    private Integer status;

    private Boolean isWinner;
}
