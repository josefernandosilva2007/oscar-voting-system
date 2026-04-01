package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.model.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<VoteModel, UUID> {
    boolean existsByUser_IdAndCategory_Id(UUID userId, UUID categoryId);

    @Query("SELECT v FROM VoteModel v JOIN FETCH v.category JOIN FETCH v.movie WHERE v.user.id = :userId")
    List<VoteModel> findByUser_Id(UUID userId);

    @Query("""
        SELECT new com.fear.oscar_voting_system.dto.ResponseUserScoreDTO(
        u.id,
        u.username,
        CAST(COUNT(CASE WHEN v.movie.id = c.movieWinning.id THEN 1 END) AS int),
        u.profilePictureUrl
        )
        FROM UserModel u
        LEFT JOIN u.votes v
        LEFT JOIN v.category c
        GROUP BY u.id, u.username, u.profilePictureUrl
        ORDER BY 3 DESC
""")
    List<ResponseUserScoreDTO> getRanking();
}