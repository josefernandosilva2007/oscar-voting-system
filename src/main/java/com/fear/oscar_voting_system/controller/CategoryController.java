package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.service.CategoryService;
import com.fear.oscar_voting_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryModel> saveCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryDTO));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryModel>> listAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.listAllCategories());
    }

    @PatchMapping("/{id}/winner")
    public ResponseEntity<Void> setWinner(@PathVariable UUID id, @RequestBody Map<String, UUID> body) {
        UUID movieId = body.get("movieId");
        categoryService.defineWinningMovie(id, movieId);
        return ResponseEntity.noContent().build();
    }
}