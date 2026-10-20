package com.fear.oscar_voting_system.controller;
import com.fear.oscar_voting_system.dto.NominationDTO;
import com.fear.oscar_voting_system.model.NominationModel;
import com.fear.oscar_voting_system.service.NominationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/nominations")
@RequiredArgsConstructor
public class NominationController {
    private final NominationService service;
    @GetMapping
    public List<NominationModel> list() { return service.list(); }
    @PostMapping
    public ResponseEntity<NominationModel> create(@RequestBody @Valid NominationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
}
