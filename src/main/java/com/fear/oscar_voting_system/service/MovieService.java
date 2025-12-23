package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.MovieDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public MovieModel saveMovie(MovieDTO movieDTO){
        List<CategoryModel> categories =
                categoryRepository.findAllById(movieDTO.categoriesID());
        for(CategoryModel category : categories){
            int limitMax = 5;
            if (category.getName().equalsIgnoreCase("Melhor Filme"))
                limitMax = 10;

            long moviesInCategory =
                    movieRepository.countByCategories_Id(category.getId());

            if (moviesInCategory >= limitMax)
                throw new RuntimeException("A categoria: " + category.getName() + " esta cheia");

        }
        var movieModel = new MovieModel();
        BeanUtils.copyProperties(movieDTO, movieModel);
        movieModel.setCategories(categories);
        return movieRepository.save(movieModel);
    }

    public List<MovieModel> listAllMovies(){return movieRepository.findAll();}







}