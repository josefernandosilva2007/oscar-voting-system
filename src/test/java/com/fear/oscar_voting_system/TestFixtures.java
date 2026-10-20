package com.fear.oscar_voting_system;

import com.fear.oscar_voting_system.model.*;
import java.util.UUID;

/** Valid defaults for unit tests; each scenario overrides only the relevant fields. */
public final class TestFixtures {
    private TestFixtures() {}

    public static UserModel.UserModelBuilder user() {
        return UserModel.builder()
            .id(UUID.randomUUID())
            .username("participante")
            .email("participante@example.com")
            .passwordHashed("hash-usado-apenas-em-testes");
    }

    public static CategoryModel.CategoryModelBuilder category() {
        return CategoryModel.builder().id(UUID.randomUUID()).name("Melhor Filme");
    }

    public static MovieModel.MovieModelBuilder movie() {
        return MovieModel.builder().id(UUID.randomUUID()).name("Filme de teste");
    }

    public static PersonModel person() {
        PersonModel person = new PersonModel();
        person.setId(UUID.randomUUID());
        person.setName("Pessoa de teste");
        return person;
    }

    public static NominationModel.NominationModelBuilder nomination() {
        return NominationModel.builder()
            .id(UUID.randomUUID())
            .category(category().build())
            .movie(movie().build())
            .person(person())
            .year(2026)
            .status(1);
    }

    public static VoteModel.VoteModelBuilder vote() {
        NominationModel nomination = nomination().build();
        return VoteModel.builder()
            .id(UUID.randomUUID())
            .user(user().build())
            .nomination(nomination)
            .category(nomination.getCategory());
    }
}
