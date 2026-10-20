package com.fear.oscar_voting_system.integration;

import com.fear.oscar_voting_system.dto.*;
import com.fear.oscar_voting_system.exception.BusinessException;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.service.*;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("postgres-test")
@Import(PostgresIT.Database.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PostgresIT {
    @TestConfiguration(proxyBeanMethods = false)
    static class Database {
        @Bean(destroyMethod = "close")
        EmbeddedPostgres postgres() throws IOException { return EmbeddedPostgres.start(); }
        @Bean
        DataSource dataSource(EmbeddedPostgres postgres) { return postgres.getPostgresDatabase(); }
    }

    @Autowired JdbcTemplate jdbc;
    @Autowired UserService users;
    @Autowired MovieService movies;
    @Autowired CategoryService categories;
    @Autowired NominationService nominations;
    @Autowired VoteService votes;
    @Autowired RankingService ranking;
    @Autowired UserRepository userRepository;
    @Autowired PersonRepository people;
    @Autowired NominationRepository nominationRepository;
    @Autowired VoteRepository voteRepository;

    // No test transaction: every service call must commit its own changes.
    @BeforeEach void clearDatabase() {
        jdbc.execute("TRUNCATE TABLE vote, nomination, movie, person, category, app_user CASCADE");
    }

    @Test void usesRealPostgresAndPersistsRegistrationAndProfile() {
        assertTrue(jdbc.queryForObject("select version()", String.class).startsWith("PostgreSQL"));
        var user = user("ana");
        var stored = userRepository.findById(user.getId()).orElseThrow();
        assertNotNull(stored.getCreatedAt());
        assertNotEquals("password123", stored.getPasswordHashed());
        assertEquals(user.getId(), users.executeLogin("ana", "password123").getId());
        String image = "https://example.com/" + "a".repeat(500);
        users.updateProfilePicture(user.getId(), image);
        assertEquals(image, userRepository.findById(user.getId()).orElseThrow().getImageUrl());
        System.out.println("Verified database: " + jdbc.queryForObject("select version()", String.class));
    }

    @Test void persistsVoteAndReadsNominationDetails() {
        var category = category("Ator");
        var nomination = nomination(category, 2026);
        var user = user("ana");
        var saved = votes.saveVote(new VoteDTO(user.getId(), nomination.getId()));
        assertNotNull(saved.getVotedAt());
        var result = votes.listVotesByUser(user.getId());
        assertEquals(1, result.size());
        assertEquals(saved.getId(), result.getFirst().id());
        assertEquals(nomination.getId(), result.getFirst().nominationId());
        assertEquals(category.getId(), result.getFirst().categoryId());
        assertEquals("Pessoa", result.getFirst().personName());
        assertEquals("Filme", result.getFirst().movieName());
        assertNull(result.getFirst().isWinner());
    }

    @Test void rejectsSecondVoteInSameYearButAllowsNextYear() {
        var category = category("Filme");
        var first = nomination(category, 2026);
        var other = nomination(category, 2026);
        var nextYear = nomination(category, 2027);
        var user = user("ana");
        votes.saveVote(new VoteDTO(user.getId(), first.getId()));
        assertThrows(BusinessException.class, () -> votes.saveVote(new VoteDTO(user.getId(), other.getId())));
        votes.saveVote(new VoteDTO(user.getId(), nextYear.getId()));
        assertEquals(2, voteRepository.count());
    }

    @Test void commitsWinnerReplacementWithoutChangingOtherYearsOrCategories() {
        var category = category("Filme");
        var old = nomination(category, 2026);
        var replacement = nomination(category, 2026);
        var nextYear = nomination(category, 2027);
        var otherCategory = nomination(category("Ator"), 2026);
        categories.defineWinningNomination(category.getId(), old.getId());
        categories.defineWinningNomination(category.getId(), nextYear.getId());
        categories.defineWinningNomination(otherCategory.getCategory().getId(), otherCategory.getId());
        categories.defineWinningNomination(category.getId(), replacement.getId());
        assertEquals(false, stored(old).getIsWinner());
        assertEquals(true, stored(replacement).getIsWinner());
        assertEquals(true, stored(nextYear).getIsWinner());
        assertEquals(true, stored(otherCategory).getIsWinner());
    }

    @Test void rejectsWinnerFromOtherCategoryWithoutChangingResults() {
        var category = category("Filme");
        var winner = nomination(category, 2026);
        var other = nomination(category("Ator"), 2026);
        categories.defineWinningNomination(category.getId(), winner.getId());
        assertThrows(BusinessException.class,
            () -> categories.defineWinningNomination(category.getId(), other.getId()));
        assertEquals(true, stored(winner).getIsWinner());
        assertNull(stored(other).getIsWinner());
    }

    @Test void ranksWinnersAndKeepsUsersWithNoPointsOrVotes() {
        var category = category("Filme");
        var winner = nomination(category, 2026);
        var loser = nomination(category, 2026);
        var pending = nomination(category("Ator"), 2026);
        var ana = user("ana");
        var bia = user("bia");
        var carlos = user("carlos");
        user("dora");
        votes.saveVote(new VoteDTO(ana.getId(), winner.getId()));
        votes.saveVote(new VoteDTO(ana.getId(), pending.getId()));
        votes.saveVote(new VoteDTO(bia.getId(), loser.getId()));
        votes.saveVote(new VoteDTO(carlos.getId(), winner.getId()));
        categories.defineWinningNomination(category.getId(), winner.getId());
        var scores = ranking.rankingScore();
        assertEquals(List.of("ana", "carlos", "bia", "dora"), scores.stream().map(ResponseUserScoreDTO::username).toList());
        assertEquals(List.of(1, 1, 0, 0), scores.stream().map(ResponseUserScoreDTO::score).toList());
        categories.defineWinningNomination(category.getId(), loser.getId());
        assertEquals("bia", ranking.rankingScore().getFirst().username());
    }

    @Test void databaseEnforcesUniqueUsernameAndEmail() {
        user("ana");
        assertThrows(DataIntegrityViolationException.class,
            () -> users.saveUser(new UserDTO("ana", "other@example.com", "password123")));
        assertThrows(DataIntegrityViolationException.class,
            () -> users.saveUser(new UserDTO("other", "ana@example.com", "password123")));
        assertEquals(1, userRepository.count());
    }

    @Test void databaseRejectsMissingRequiredFieldAndInvalidForeignKey() {
        var nomination = nomination(category("Filme"), 2026);
        assertThrows(DataIntegrityViolationException.class, () -> jdbc.update(
            "insert into vote (id, user_id, nomination_id, category_id, voted_at) values (?, ?, ?, ?, current_timestamp)",
            UUID.randomUUID(), UUID.randomUUID(), nomination.getId(), nomination.getCategory().getId()));
        assertThrows(DataIntegrityViolationException.class, () -> jdbc.update(
            "insert into nomination (id, category_id, movie_id, person_id, year, status) values (?, ?, ?, ?, ?, null)",
            UUID.randomUUID(), nomination.getCategory().getId(), nomination.getMovie().getId(), nomination.getPerson().getId(), 2026));
        assertEquals(0, voteRepository.count());
    }

    private UserModel user(String name) {
        return users.saveUser(new UserDTO(name, name + "@example.com", "password123"));
    }
    private CategoryModel category(String name) { return categories.saveCategory(new CategoryDTO(name)); }
    private NominationModel nomination(CategoryModel category, int year) {
        var person = new PersonModel();
        person.setName("Pessoa");
        person = people.saveAndFlush(person);
        var movie = movies.saveMovie(new MovieDTO("Filme", person.getId(), "Sinopse"));
        return nominations.create(new NominationDTO(category.getId(), movie.getId(), person.getId(), year, 1));
    }
    private NominationModel stored(NominationModel nomination) {
        return nominationRepository.findById(nomination.getId()).orElseThrow();
    }
}
