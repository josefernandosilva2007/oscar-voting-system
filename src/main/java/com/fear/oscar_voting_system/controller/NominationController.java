package com.fear.oscar_voting_system.controller;
import com.fear.oscar_voting_system.dto.NominationDTO;
import com.fear.oscar_voting_system.model.NominationModel;
import com.fear.oscar_voting_system.service.NominationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/nominations")
@RequiredArgsConstructor
public class NominationController {
    private final NominationService service;
    @GetMapping
    public List<NominationModel> list() { return service.list(); }
    @PatchMapping("/{id}")
    public NominationModel update(@PathVariable UUID id, @RequestBody @Valid NominationDTO dto) {
        return service.update(id, dto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) { service.delete(id); }
    @GetMapping("/ceremony/{year}/progress")
    public com.fear.oscar_voting_system.dto.CeremonyProgressDTO progress(@PathVariable int year) {
        return service.progress(year);
    }
    @PostMapping
    public ResponseEntity<NominationModel> create(@RequestBody @Valid NominationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
}
