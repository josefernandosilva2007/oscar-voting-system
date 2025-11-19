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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}