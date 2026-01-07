package com.fear.oscar_voting_system.config;

import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

// so pra teste
@Configuration
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar dados se já rodou antes
        if (categoryRepository.count() > 0) {
            return;
        }

        System.out.println("🌱 SEEDER: Criando dados iniciais com ManyToMany...");

        // 1. Criar Categorias
        CategoryModel bestPicture = new CategoryModel();
        bestPicture.setName("Melhor Filme");
        categoryRepository.save(bestPicture);

        CategoryModel bestDirector = new CategoryModel();
        bestDirector.setName("Melhor Diretor");
        categoryRepository.save(bestDirector);

        CategoryModel bestActor = new CategoryModel();
        bestActor.setName("Melhor Ator");
        categoryRepository.save(bestActor);

        // 2. Criar Filmes/Diretor/Autor e ligar nas categorias
        criarFilme(
                "Oppenheimer",
                "Christopher Nolan",
                "https://br.web.img2.acsta.net/pictures/23/05/08/10/29/0695770.jpg",
                Arrays.asList(bestPicture, bestDirector)
        );
        criarFilme(
                "Barbie",
                "Greta Gerwig",
                "https://uauposters.com.br/media/catalog/product/4/5/454520230615-uau-posters-barbie-2023-filmes-1.jpg",
                Arrays.asList(bestPicture)
        );
        criarFilme(
                "Assassinos da Lua das Flores",
                "Martin Scorsese",
                "https://m.media-amazon.com/images/M/MV5BOGMyOTJjYWItMzBkYy00ZGRkLWI4MzMtMTE1YzhiZjE1NTE4XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestPicture, bestDirector)
        );
        criarFilme(
                "Pobres Criaturas",
                "Yorgos Lanthimos",
                "https://m.media-amazon.com/images/M/MV5BYWFiN2UwNjktZTY2OS00YzMzLWE3ODItOWI1MTAxNjk4ZDFmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestPicture, bestDirector)
        );
        // Criando um Ator
        criarFilme(
                "Cillian Murphy",
                "Oppenheimer",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHeg5k-996G1zIdfaAKNwnMmuTCd4-6XlbYJOfJaBPvHXb-CH2gmdHh3kJs5IlthXmZv7ub7ovdRqFGhbIhEmqbBSK8zzXRl3AW4td_Js&s=10",
                Arrays.asList(bestActor)
        );
        criarFilme(
                "Leonardo Di Caprio",
                "Assassinos da Lua das Flores",
                "https://m.media-amazon.com/images/M/MV5BZTU2MDczYTAtMmE1Ny00ODE1LTg3MDgtODJmN2JlOTIwYzEyXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActor)
        );

        System.out.println("Filmes e Categorias criados!");
    }

    private void criarFilme(String nome, String diretor, String imageUrl, List<CategoryModel> categorias) {
        MovieModel movie = new MovieModel();
        movie.setName(nome);
        movie.setDirector(diretor);
        movie.setImageUrl(imageUrl);
        movie.setCategories(categorias);
        movieRepository.save(movie);
    }
}