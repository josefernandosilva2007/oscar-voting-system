package com.fear.oscar_voting_system.repository;
import com.fear.oscar_voting_system.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface PersonRepository extends JpaRepository<PersonModel, UUID> {}
