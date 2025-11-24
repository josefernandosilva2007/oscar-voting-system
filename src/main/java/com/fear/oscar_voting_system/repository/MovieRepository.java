package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, UUID> {
    long countByCategories_Id(UUID categoryId);
}