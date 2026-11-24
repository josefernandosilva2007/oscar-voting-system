package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.model.AssignedAward;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AssignedAwardRepository extends JpaRepository<AssignedAward, UUID> {
    boolean existsByRecipientIdAndLabelIgnoreCase(UUID recipientId, String label);
}
