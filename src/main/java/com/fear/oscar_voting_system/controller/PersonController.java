package com.fear.oscar_voting_system.controller;
import com.fear.oscar_voting_system.dto.PersonDTO;
import com.fear.oscar_voting_system.model.PersonModel;
import com.fear.oscar_voting_system.repository.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {
    private final PersonRepository repository;
    @GetMapping
    public List<PersonModel> list() { return repository.findAll(); }
    @PatchMapping("/{id}")
    public PersonModel update(@PathVariable UUID id, @RequestBody @Valid PersonDTO dto) {
        PersonModel person = repository.findById(id)
            .orElseThrow(() -> new com.fear.oscar_voting_system.exception.ResourceNotFoundException("Pessoa não encontrada"));
        person.setName(dto.name());
        person.setImageUrl(dto.imageUrl());
        return repository.save(person);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { repository.deleteById(id); }
    @PostMapping
    public ResponseEntity<PersonModel> create(@RequestBody @Valid PersonDTO dto) {
        PersonModel person = new PersonModel();
        person.setName(dto.name());
        person.setImageUrl(dto.imageUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(person));
    }
}
