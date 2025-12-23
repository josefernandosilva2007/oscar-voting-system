package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.dto.UserDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MovieRepository movieRepository;

    public CategoryModel saveCategory(CategoryDTO categoryDTO){
        var categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryDTO, categoryModel);
        return categoryRepository.save(categoryModel);
    }

    public void defineWinningMovie(UUID categoryId, UUID movieId){
        MovieModel movie =
                movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Esse Filme nao existe"));
        CategoryModel category =
                categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Essa Categoria nao Existe"));

        if (!movie.getCategories().contains(category))
            throw new RuntimeException("O filme " + movie.getName() + " nao concorre a categoria " + category.getName());

        category.setMovieWinning(movie);
        categoryRepository.save(category);
    }

    public List<CategoryModel> listAllCategories(){
        return categoryRepository.findAll();
    }

}