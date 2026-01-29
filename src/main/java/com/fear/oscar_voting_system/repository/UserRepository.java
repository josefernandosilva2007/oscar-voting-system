package com.fear.oscar_voting_system.repository;

import com.fear.oscar_voting_system.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel u SET u.profilePictureUrl = :url WHERE u.id = :id ")
    void updateProfilePicture(@Param("id") UUID id, @Param("url") String url);
}