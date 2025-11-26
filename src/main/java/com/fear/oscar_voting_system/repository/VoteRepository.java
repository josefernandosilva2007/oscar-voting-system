package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.model.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<VoteModel, UUID> {
    boolean existsByUser_IdAndCategory_Id(UUID userId, UUID categoryId);
}