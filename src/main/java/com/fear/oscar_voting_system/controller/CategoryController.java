package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryModel> saveCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> listAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.listAllCategories());
    }

    @PatchMapping("/{id}/winner")
    public ResponseEntity<Void> setWinner(@PathVariable UUID id, @RequestBody @Valid com.fear.oscar_voting_system.dto.WinnerDTO body) {
        categoryService.defineWinningNomination(id, body.nominationId());
        return ResponseEntity.noContent().build();
    }
}