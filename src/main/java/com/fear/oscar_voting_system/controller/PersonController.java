package com.fear.oscar_voting_system.controller;
import com.fear.oscar_voting_system.dto.PersonDTO;
import com.fear.oscar_voting_system.model.PersonModel;
import com.fear.oscar_voting_system.repository.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {
    private final PersonRepository repository;
    @GetMapping
    public List<PersonModel> list() { return repository.findAll(); }
    @PostMapping
    public ResponseEntity<PersonModel> create(@RequestBody @Valid PersonDTO dto) {
        PersonModel person = new PersonModel();
        person.setName(dto.name());
        person.setImageUrl(dto.imageUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(person));
    }
}
