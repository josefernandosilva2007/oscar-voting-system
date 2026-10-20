package com.fear.oscar_voting_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

@lombok.Data
@lombok.NoArgsConstructor
@Entity
@Table(name = "person")
public class PersonModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Size(max = 100, message = "The name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    private String imageUrl;
}
