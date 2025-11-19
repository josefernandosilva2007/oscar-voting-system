package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryModel saveCategory(CategoryDTO categoryDTO){
        var categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDTO, categoryModel);
        return categoryRepository.save(categoryModel);
    }

    public List<CategoryModel> listAllCategories(){
        return categoryRepository.findAll();
    }

}