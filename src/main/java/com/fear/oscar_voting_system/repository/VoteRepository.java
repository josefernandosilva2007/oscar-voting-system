package com.fear.oscar_voting_system.repository;
import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.model.VoteModel;
import org.springframework.data.jpa.repository.*;
import java.util.*;
public interface VoteRepository extends JpaRepository<VoteModel, UUID> {
    boolean existsByUser_IdAndCategory_IdAndNomination_Year(UUID userId, UUID categoryId, int year);
    List<VoteModel> findByUser_Id(UUID userId);
    @Query("""
        SELECT new com.fear.oscar_voting_system.dto.ResponseUserScoreDTO(
            u.id, u.username,
            CAST(SUM(CASE WHEN n.isWinner = true THEN 1 ELSE 0 END) AS int), u.imageUrl)
        FROM UserModel u LEFT JOIN u.votes v LEFT JOIN v.nomination n
        GROUP BY u.id, u.username, u.imageUrl
        ORDER BY 3 DESC, u.username ASC
        """)
    List<ResponseUserScoreDTO> getRanking();
}
