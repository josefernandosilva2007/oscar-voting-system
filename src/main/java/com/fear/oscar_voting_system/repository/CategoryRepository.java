package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
}