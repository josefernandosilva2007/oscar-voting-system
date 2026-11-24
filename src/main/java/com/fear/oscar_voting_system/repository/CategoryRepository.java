package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    @org.springframework.data.jpa.repository.Query("select count(c) from CategoryModel c where lower(trim(c.name)) = lower(:name)")
    long countByNormalizedName(String name);
}
