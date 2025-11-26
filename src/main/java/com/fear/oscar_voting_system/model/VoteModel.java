package com.fear.oscar_voting_system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Vote", uniqueConstraints = {
        @UniqueConstraint(
                name = "UniqueUserAndCategory",
                columnNames = {"user_id", "category_id"} )
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    @ManyToOne
    @JoinColumn(name= "movie_id")
    private MovieModel movie;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;
}