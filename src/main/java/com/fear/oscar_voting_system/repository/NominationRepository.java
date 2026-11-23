package com.fear.oscar_voting_system.repository;
import com.fear.oscar_voting_system.model.NominationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;
public interface NominationRepository extends JpaRepository<NominationModel, UUID> {
    List<NominationModel> findByCategory_IdAndYear(UUID categoryId, int year);
    @Query("select count(distinct n.category.id) from NominationModel n where n.year = :year")
    long countDistinctByYear(int year);
    @Query("select count(distinct n.category.id) from NominationModel n where n.year = :year and n.isWinner is not null")
    long countDistinctByYearAndIsWinnerIsNotNull(int year);
}
