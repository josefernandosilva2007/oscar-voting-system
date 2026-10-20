package com.fear.oscar_voting_system.repository;
import com.fear.oscar_voting_system.model.NominationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface NominationRepository extends JpaRepository<NominationModel, UUID> {
    List<NominationModel> findByCategory_IdAndYear(UUID categoryId, int year);
}
