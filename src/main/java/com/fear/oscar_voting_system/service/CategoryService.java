package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryModel saveUser(CategoryDTO categoryDTO){
        var categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDTO, categoryModel);
        return categoryRepository.save(categoryModel);
    }

}