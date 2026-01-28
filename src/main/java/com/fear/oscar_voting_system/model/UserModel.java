package com.fear.oscar_voting_system.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Data
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String profilePictureUrl;
}