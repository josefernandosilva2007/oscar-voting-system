package com.fear.oscar_voting_system.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class CatalogLookup {
    private static final Set<String> IMDB = Set.of("www.imdb.com", "imdb.com", "m.imdb.com");
    private static final Set<String> TMDB = Set.of("www.themoviedb.org", "themoviedb.org", "tmdb.org", "www.tmdb.org");
    private static final Pattern IMDB_PATH = Pattern.compile("/(?:[a-z]{2}(?:-[A-Za-z]{2})?/)?(title/tt|name/nm)([0-9]{1,12})(?:/.*)?");
    private static final Pattern TMDB_PATH = Pattern.compile("/(movie|person)/([0-9]{1,10})(?:-[^/]*)?(?:/.*)?");
    private final RestClient client;
    private final String token;

    public record Preview(String name, String synopsis, String imageUrl) {}

    @Autowired
    public CatalogLookup(@Value("${TMDB_READ_ACCESS_TOKEN:}") String token) {
        this(token, tmdbClient());
    }

    CatalogLookup(String token, RestClient client) {
        this.token = token.strip();
        this.client = client;
    }

    private static RestClient tmdbClient() {
        var http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();
        var factory = new JdkClientHttpRequestFactory(http);
        factory.setReadTimeout(Duration.ofSeconds(8));
        return RestClient.builder().requestFactory(factory).baseUrl("https://api.themoviedb.org/3").build();
    }

    public Preview lookup(String link, String type) {
        URI uri;
        try {
            uri = URI.create(link.strip());
        } catch (IllegalArgumentException e) {
            throw invalidLink();
        }
        if (uri.getScheme() == null || uri.getHost() == null || uri.getUserInfo() != null || uri.getPort() != -1
                || !Set.of("https", "http").contains(uri.getScheme().toLowerCase(Locale.ROOT))) throw invalidLink();
        String host = uri.getHost().toLowerCase(Locale.ROOT);
        boolean imdb = IMDB.contains(host);
        if (!imdb && !TMDB.contains(host)) throw invalidLink();
        var match = (imdb ? IMDB_PATH : TMDB_PATH).matcher(uri.getRawPath());
        if (!match.matches()) throw invalidLink();
        boolean movie = Set.of("movie", "title/tt").contains(match.group(1));
        if (!type.equals(movie ? "movie" : "person"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, movie
                    ? "Use este link em Novo filme." : "Use este link em Nova pessoa.");
        if (token.isEmpty()) throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Configure TMDB_READ_ACCESS_TOKEN no backend para importar dados.");

        String id = match.group(2);
        String path = imdb ? "/find/" + (movie ? "tt" : "nm") + id + "?external_source=imdb_id&language=pt-BR"
                           : "/" + type + "/" + id + "?language=pt-BR";
        JsonNode result;
        try {
            result = client.get().uri(path).headers(h -> h.setBearerAuth(token)).retrieve().body(JsonNode.class);
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().value() == 404) throw missing();
            if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 403)
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        "Credencial recusada pelo TMDB. Confira o token no backend.");
            if (e.getStatusCode().value() == 429) throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Limite de consultas do TMDB atingido. Tente novamente em instantes.");
            throw unavailable();
        } catch (RestClientException e) {
            throw unavailable();
        }
        if (result == null) throw unavailable();
        if (imdb) result = result.path(type + "_results").path(0);
        String name = result.path(movie ? "title" : "name").asText("");
        if (name.isBlank()) throw missing();
        String image = result.path(movie ? "poster_path" : "profile_path").asText("");
        String imageUrl = image.matches("/[A-Za-z0-9._-]+") ? "https://image.tmdb.org/t/p/"
                + (movie ? "w500" : "w185") + image : null;
        return new Preview(name, movie ? result.path("overview").asText("") : null, imageUrl);
    }

    private static ResponseStatusException invalidLink() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cole um link de filme ou pessoa do IMDb ou TMDB.");
    }

    private static ResponseStatusException missing() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum cadastro correspondente foi encontrado no TMDB.");
    }

    private static ResponseStatusException unavailable() {
        return new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Consulta ao TMDB indisponível. Tente novamente mais tarde.");
    }
}
