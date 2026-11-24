package com.fear.oscar_voting_system.controller;

import com.fear.oscar_voting_system.service.CatalogLookup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogLookup catalog;

    public record Request(@NotBlank @Size(max = 2048) String url,
                          @NotBlank @Pattern(regexp = "movie|person") String type) {}

    @PostMapping("/catalog/preview")
    public CatalogLookup.Preview preview(@Valid @RequestBody Request request) {
        return catalog.lookup(request.url(), request.type());
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> lookupError(ResponseStatusException error) {
        return ResponseEntity.status(error.getStatusCode()).body(Map.of("message", error.getReason()));
    }
}
