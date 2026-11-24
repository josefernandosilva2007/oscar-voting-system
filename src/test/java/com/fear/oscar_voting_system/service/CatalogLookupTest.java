package com.fear.oscar_voting_system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class CatalogLookupTest {
    private final RestClient.Builder builder = RestClient.builder().baseUrl("https://api.themoviedb.org/3");
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final CatalogLookup lookup = new CatalogLookup("test", builder.build());

    @Test void readsMovieFromImdbFindResults() {
        server.expect(requestTo("https://api.themoviedb.org/3/find/tt1234567?external_source=imdb_id&language=pt-BR"))
                .andExpect(header("Authorization", "Bearer test"))
                .andRespond(withSuccess("""
                        {"movie_results":[{"title":"Filme","overview":"Sinopse","poster_path":"/poster.jpg"}]}
                        """, MediaType.APPLICATION_JSON));
        assertEquals(new CatalogLookup.Preview("Filme", "Sinopse", "https://image.tmdb.org/t/p/w500/poster.jpg"),
                lookup.lookup("https://www.imdb.com/pt/title/tt1234567/?ref_=share", "movie"));
        server.verify();
    }

    @Test void readsPersonWithNoPhoto() {
        server.expect(requestTo("https://api.themoviedb.org/3/person/123?language=pt-BR"))
                .andRespond(withSuccess("{\"name\":\"Pessoa\",\"profile_path\":null}", MediaType.APPLICATION_JSON));
        assertEquals(new CatalogLookup.Preview("Pessoa", null, null),
                lookup.lookup("https://www.themoviedb.org/person/123-nome?language=en", "person"));
        server.verify();
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://example.com/movie/123", "//imdb.com/title/tt123", "https://imdb.com/name/nm123"})
    void rejectsInvalidOrMismatchedMovieLink(String url) {
        assertEquals(400, assertThrows(ResponseStatusException.class, () -> lookup.lookup(url, "movie"))
                .getStatusCode().value());
    }
}
