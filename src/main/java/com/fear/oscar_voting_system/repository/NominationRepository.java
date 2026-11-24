package com.fear.oscar_voting_system.repository;
import com.fear.oscar_voting_system.model.NominationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;
public interface NominationRepository extends JpaRepository<NominationModel, UUID> {
    @Query("select n.year from NominationModel n where n.year > 2025 group by n.year having count(distinct n.category.id) = count(distinct case when n.isWinner is not null then n.category.id else null end) order by n.year desc")
    List<Integer> completedAwardYears();
    List<NominationModel> findByCategory_IdAndYear(UUID categoryId, int year);
    boolean existsByCategory_IdAndYearAndIsWinnerTrue(UUID categoryId, int year);
    @Query("select count(distinct n.category.id) from NominationModel n where n.year = :year")
    long countDistinctByYear(int year);
    @Query("select count(distinct n.category.id) from NominationModel n where n.year = :year and n.isWinner is not null")
    long countDistinctByYearAndIsWinnerIsNotNull(int year);
}
