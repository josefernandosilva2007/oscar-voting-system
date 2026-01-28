package com.fear.oscar_voting_system.config;

import com.fear.oscar_voting_system.dto.MovieDTO;
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
        // MELHOR FILME
        CategoryModel bestPicture = new CategoryModel();
        bestPicture.setName("Melhor Filme");
        categoryRepository.save(bestPicture);

        // MELHOR DIRECAO
        CategoryModel bestDirecting = new CategoryModel();
        bestDirecting.setName("Melhor Direção");
        categoryRepository.save(bestDirecting);

        // MElHOR ATOR
        CategoryModel bestActorInALeadingRole = new CategoryModel();
        bestActorInALeadingRole.setName("Melhor Ator");
        categoryRepository.save(bestActorInALeadingRole);

        // MELHOR ATOR COADJUVANTE
        CategoryModel bestActorInASupportingRole = new CategoryModel();
        bestActorInASupportingRole.setName("Melhor Ator Coadjuvante");
        categoryRepository.save(bestActorInASupportingRole);

        //MELHOR ATRIZ
        CategoryModel bestActressInALeadingRole = new CategoryModel();
        bestActressInALeadingRole.setName("Melhor Atriz");
        categoryRepository.save(bestActressInALeadingRole);

        // MELHOR ATRIZ COADJUVANTE
        CategoryModel bestActressInASupportingRole = new CategoryModel();
        bestActressInASupportingRole.setName("Melhor Atriz Coadjuvante");
        categoryRepository.save(bestActressInASupportingRole);


        //MELHOR FILME DE ANIMACAO
        CategoryModel bestAnimatedFeatureFilm = new CategoryModel();
        bestAnimatedFeatureFilm.setName("Melhor Animação");
        categoryRepository.save(bestAnimatedFeatureFilm);

        // MELHOR CURTA ANIMADO
        CategoryModel bestAnimatedShortFilm = new CategoryModel();
        bestAnimatedShortFilm.setName("Melhor Curta Animação");
        categoryRepository.save(bestAnimatedShortFilm);

        // MELHOR ELENCO
        CategoryModel bestCasting = new CategoryModel();
        bestCasting.setName("Melhor Elenco");
        categoryRepository.save(bestCasting);

        // MELHOR FIGURINO
        CategoryModel bestCostumeDesign = new CategoryModel();
        bestCostumeDesign.setName("Melhor Figurino");
        categoryRepository.save(bestCostumeDesign);

        // MELHOR DOCUMENTARIO
        CategoryModel bestDocumentaryFeatureFilm = new CategoryModel();
        bestDocumentaryFeatureFilm.setName("Melhor Documentario");
        categoryRepository.save(bestDocumentaryFeatureFilm);

        // MELHOR CURTA DOCUMENTARIO
        CategoryModel bestDocumentaryShortFilm = new CategoryModel();
        bestDocumentaryShortFilm.setName("Melhor Documentario em Curta-Metragem");
        categoryRepository.save(bestDocumentaryShortFilm);

        // MELHOR MONTAGEM
        CategoryModel bestFilmEditing = new CategoryModel();
        bestFilmEditing.setName("Melhor Montagem");
        categoryRepository.save(bestFilmEditing);

        // MELHOR FILM INTERNACIONAL
        CategoryModel bestInternacionalFeatureFilm = new CategoryModel();
        bestInternacionalFeatureFilm.setName("Melhor Filme Internacional");
        categoryRepository.save(bestInternacionalFeatureFilm);

        // MELHOR CURTA-METRAGEM EM LIVE ACTION
        CategoryModel bestLiveActionShortFilm = new CategoryModel();
        bestLiveActionShortFilm.setName("Melhor Curta-Metragem em Live Action");
        categoryRepository.save(bestLiveActionShortFilm);

        // MELHOR CABELO E MAQUIAGEM
        CategoryModel bestMakeupAndHairstyling = new CategoryModel();
        bestMakeupAndHairstyling.setName("Melhor Cabelo e Maquiagem");
        categoryRepository.save(bestMakeupAndHairstyling);

        // MELHOR TRILHA SONORA ORIGINAL
        CategoryModel bestMusicOriginalScore = new CategoryModel();
        bestMusicOriginalScore.setName("Melhor Trilha Sonora Original");
        categoryRepository.save(bestMusicOriginalScore);

        // MELHOR MUSICA ORIGINAL
        CategoryModel bestMusicOriginalSong = new CategoryModel();
        bestMusicOriginalSong.setName("Melhor Musica Original");
        categoryRepository.save(bestMusicOriginalSong);

        // MELHOR DESIGN DE PRODUCAO
        CategoryModel bestProductionDesign = new CategoryModel();
        bestProductionDesign.setName("Melhor Design de Produção");
        categoryRepository.save(bestProductionDesign);

        // MELHOR SOM
        CategoryModel bestSound = new CategoryModel();
        bestSound.setName("Melhor Som");
        categoryRepository.save(bestSound);

        // MELHOR EFEITOS ESPECIAIS
        CategoryModel bestVisualEffects = new CategoryModel();
        bestVisualEffects.setName("Melhor Efeitos Especiais");
        categoryRepository.save(bestVisualEffects);

        // MELHOR ROTEIRO ADAPTADO
        CategoryModel bestWritingAdaptedScreenplay = new CategoryModel();
        bestWritingAdaptedScreenplay.setName("Melhor Roteiro Adaptado");
        categoryRepository.save(bestWritingAdaptedScreenplay);

        // MELHOR ROTEIRO ORIGINAL
        CategoryModel bestWritingOriginalScreenplay = new CategoryModel();
        bestWritingOriginalScreenplay.setName("Melhor Roteiro Original");
        categoryRepository.save(bestWritingOriginalScreenplay);

        // MELHOR FOTOGRAFIA
        CategoryModel bestCinematography = new CategoryModel();
        bestCinematography.setName("Melhor Fotografia");
        categoryRepository.save(bestCinematography);


        // PECADORES
        criarFilme(
                "Pecadores",
                "Ryan Coogler",
                "https://m.media-amazon.com/images/M/MV5BMjVhNTQ4NjQtODgzMy00OGM1LTkzOWEtYmU2Mzg0YzUyZjA3XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestPicture,
                        bestDirecting,
                        bestWritingOriginalScreenplay,
                        bestCasting,
                        bestProductionDesign,
                        bestMusicOriginalScore,
                        bestCostumeDesign,
                        bestMakeupAndHairstyling,
                        bestFilmEditing,
                        bestVisualEffects,
                        bestCinematography,
                        bestSound
                        )
        );

        // PREMIOS DE ATOR/ATRIZ PECADORES
        criarFilme(
                "Michael B. Jordan",
                "Pecadores",
                "https://m.media-amazon.com/images/M/MV5BMjExOTY3NzExM15BMl5BanBnXkFtZTgwOTg1OTAzMTE@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActorInALeadingRole)
        );
        criarFilme(
                "Delroy Lindo",
                "Pecadores",
                "https://br.web.img2.acsta.net/c_310_420/pictures/20/09/16/19/52/1635397.jpg",
                Arrays.asList(bestActorInASupportingRole)
        );
        criarFilme(
                "Wunmi Mosaku",
                "Pecadores",
                "https://m.media-amazon.com/images/M/MV5BZTViMjhmNzYtZjNlYi00ZDk3LTlkODEtNGEyYjc4NjEyYWVkXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActressInASupportingRole)
        );

        // Marty Supreme
        criarFilme(
                "Marty Supreme",
                "Josh Safdie",
                "https://ingresso-a.akamaihd.net/prd/img/movie/marty-supreme/152a01c7-8475-40c0-a56e-c2ca16382e65.webp",
                Arrays.asList(
                        bestPicture,
                        bestDirecting,
                        bestWritingOriginalScreenplay,
                        bestCinematography,
                        bestFilmEditing,
                        bestProductionDesign,
                        bestCostumeDesign,
                        bestCasting
                        )
        );

        // PREMIOS DE ATOR/ATRIZ MARTY SUPREME
        criarFilme(
                "Timothée Chalamet",
                "Marty Supreme",
                "https://m.media-amazon.com/images/M/MV5BZTIyZWY4ZjktOGJiZi00NGFkLTllMjctZjJjMmNiMjIxOTY2XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActorInALeadingRole)
                );

        // UMA BATALHA APOS A OUTRA
        criarFilme(
                "Uma Batalha Após a Outra",
                "Paul Thomas Anderson",
                "https://m.media-amazon.com/images/M/MV5BNDFiMGM0OTEtNzI1OS00Mjk2LTg3MDEtYjYyODgzMTMzNjJhXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestPicture,
                        bestDirecting,
                        bestWritingAdaptedScreenplay,
                        bestCinematography,
                        bestFilmEditing,
                        bestProductionDesign,
                        bestMusicOriginalScore,
                        bestSound,
                        bestCasting
                )
        );

        // PREMIOS DE ATOR/ATRIZ UMA BATALHA APOS OUTRA
        criarFilme(
                "Leonardo DiCaprio",
                "Uma Batalha Após a Outra",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAoUmJOw0YOeyZs8L7RhsmB3D2RZwh-XR59A&s",
                Arrays.asList(bestActorInALeadingRole)
        );
        criarFilme(
                "Benicio del Toro",
                "Uma Batalha Após a Outra",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiuukjRLiLuI6bfyFK20IrLJ5SDmjz7jDeYg&s",
                Arrays.asList(bestActorInASupportingRole)
        );
        criarFilme(
                "Sean Penn",
                "Uma Batalha Após a Outra",
                "https://m.media-amazon.com/images/M/MV5BMTc1NjMzMjY3NF5BMl5BanBnXkFtZTcwMzkxNjQzMg@@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActorInASupportingRole)
        );
        criarFilme(
                "Teyana Taylor",
                "Uma Batalha Após a Outra",
                "https://m.media-amazon.com/images/M/MV5BMzBjNjZiODgtZWU1Yy00MzJlLTg3NmEtMTU3MjcwN2Q3ZmU4XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActressInASupportingRole)
        );

        // HAMNET
        criarFilme(
                "Hamnet",
                "Chloé Zhao",
                "https://m.media-amazon.com/images/M/MV5BMDQ5ZmY0OWYtOTYzZi00Mzg5LWE3N2EtMjYwZTAzZmJhYjkyXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestPicture,
                        bestDirecting,
                        bestWritingAdaptedScreenplay,
                        bestProductionDesign,
                        bestMusicOriginalScore,
                        bestCostumeDesign,
                        bestCasting
                )
        );

        // PREMIOS ATOR/ATRIZ HAMNET
        criarFilme(
                "Jessie Buckley",
                "Hamnet",
                "https://m.media-amazon.com/images/M/MV5BNTlkMzVlZDktMjYwZi00NDQ5LTgwOGItZDA4NTRmMTAzZWJiXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(bestActressInALeadingRole)
        );
//        criarFilme(
//                "Paul Mescal",
//                "Hamnet",
//                "https://upload.wikimedia.org/wikipedia/commons/4/46/Paul_Mescal_at_the_Toronto_International_Film_Festival_in_2025_2_%28cropped_2%29.jpg",
//                Arrays.asList(bestActorInASupportingRole)
//        );


        // VALOR SENTIMENTAL
        criarFilme(
                "Valor Sentimental",
                "Joachim Trier",
                "https://m.media-amazon.com/images/M/MV5BZGI2MTk1MGEtZTA5OC00MWIwLTk0YjAtNmY5NjIyODUxNTk1XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestPicture,
                        bestDirecting,
                        bestWritingOriginalScreenplay,
                        bestInternacionalFeatureFilm,
                        bestFilmEditing
                )
        );

        // PREMIOS ATOR/ATRIZ VALOR SENTIMENTAL
        criarFilme(
                "Renate Reinsve",
                "Valor Sentimental",
                "https://m.media-amazon.com/images/M/MV5BZDliODgyNzctZjE3ZC00NDA1LWFhYTItZWNlMTcwMzc4MjFmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActressInALeadingRole)
        );
        criarFilme(
                "Stellan Skarsgård",
                "Valor Sentimental",
                "https://m.media-amazon.com/images/M/MV5BODg3MzBhYzQtOTQyMC00MTMyLTg4MjEtMjRlZjFkMWM2YzAxXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(bestActorInASupportingRole)
        );
        criarFilme(
                "Elle Fanning",
                "Valor Sentimental",
                "https://m.media-amazon.com/images/M/MV5BZmY4ZTUxOTEtZWI4ZC00NDAzLTllZTgtMjQ4MDUwNWYyNTkxXkEyXkFqcGc@._V1_CR520,59,1221,1832_.jpg",
                Arrays.asList(bestActressInASupportingRole)
        );
        criarFilme(
                "Inga Ibsdotter Lilleaas",
                "Valor Sentimental",
                "https://m.media-amazon.com/images/M/MV5BNWUzOGU4YmEtMDAwYy00OWQ4LTkyYmQtMGU4OWJmMTgyM2M0XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(bestActressInASupportingRole)
        );

        // AGENTE SECRETO
        criarFilme(
                "O Agente Secreto",
                "Kleber Mendonça Filho",
                "https://br.web.img3.acsta.net/r_1280_720/img/d5/da/d5dae089c320b75bf1903cc249165073.jpg",
                Arrays.asList(
                        bestPicture,
                        bestInternacionalFeatureFilm,
                        bestCasting
                )
        );

        // PREMIOS ATOR/ATRIZ O AGENTE SECRETO
        criarFilme(
                "Wagner Moura",
                "O Agente Secreto",
                "https://br.web.img3.acsta.net/pictures/18/07/25/21/22/3529943.jpg",
                Arrays.asList(bestActorInALeadingRole)
        );

        // FRANKENSTEIN
        criarFilme(
                "Frankenstein",
                "Guillermo del Toro",
                "https://m.media-amazon.com/images/M/MV5BYzYzNDYxMTQtMTU4OS00MTdlLThhMTQtZjI4NGJmMTZmNmRiXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestPicture,
                        bestWritingAdaptedScreenplay,
                        bestProductionDesign,
                        bestCostumeDesign,
                        bestMakeupAndHairstyling,
                        bestCinematography,
                        bestMusicOriginalScore,
                        bestSound
                )
        );

        // PREMIOS ATOR/ATRIZ FRANKENSTEIN
        criarFilme(
                "Jacob Elordi",
                "Frankenstein",
                "https://m.media-amazon.com/images/M/MV5BZmI4MDM3ZWEtNzhhMi00MzQzLWI4MmUtZjliMzU1NzhhM2FiXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(bestActorInASupportingRole)
        );


        // BUGONIA
        criarFilme(
                "Bugonia",
                "Yorgos Lanthimos",
                "https://acdn-us.mitiendanube.com/stores/004/687/740/products/pos-04434-7c6751156200cd681717622612866477-1024-1024.webp",
                Arrays.asList(
                        bestPicture,
                        bestWritingAdaptedScreenplay,
                        bestMusicOriginalScore
                )
        );

        // PREMIOS ATOR/ATRIZ BUGONIA
        criarFilme(
                "Emma Stone",
                "Bugonia",
                "https://m.media-amazon.com/images/M/MV5BMjI4NjM1NDkyN15BMl5BanBnXkFtZTgwODgyNTY1MjE@._V1_.jpg",
                Arrays.asList(bestActressInALeadingRole)
        );

        // IMAGENS A PARTIR DAQUI

        // LUA AZUL
        criarFilme(
                "Lua Azul",
                "Richard Linklater",
                "https://br.web.img2.acsta.net/pictures/21/09/22/09/23/3406897.jpg",
                Arrays.asList(
                        bestWritingOriginalScreenplay
                )
        );

        // PREMIOS ATOR/ATRIZ LUA AZUL
        criarFilme(
                "Ethan Hawke",
                "Blue Moon",
                "https://upload.wikimedia.org/wikipedia/commons/4/4e/BlueMoonBFILFF161025-53_%2854875551295%29_%28cropped_2%29.jpg",
                Arrays.asList(bestActorInALeadingRole)
        );

        // AVATAR
        criarFilme(
                "Avatar: Fogo e Cinzas",
                "James Cameron",
                "https://lumiere-a.akamaihd.net/v1/images/avatar-2_e40e26de.jpeg",
                Arrays.asList(
                        bestCostumeDesign,
                        bestVisualEffects
                )
        );

        // JURASSIC WORLD RECOMECO
        criarFilme(
                "Jurassic World: Recomeço",
                "Gareth Edwards",
                "https://ingresso-a.akamaihd.net/b2b/production/uploads/movie/poster/1181/2f3dcf436591f4dd26762afdd9f714ea.jpg",
                Arrays.asList(
                        bestVisualEffects
                )
        );

        //O Ônibus Perdido
        criarFilme(
                "O Ônibus Perdido",
                "Paul Greengrass",
                "https://m.media-amazon.com/images/M/MV5BZTIzNmQzYzUtNTdlNi00NmY5LThmNTYtMGFmZjUxMTgzOGNmXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestVisualEffects
                )
        );

        // F1
        criarFilme(
                "F1",
                "Joseph Kosinski",
                "https://m.media-amazon.com/images/M/MV5BNGI0MDI4NjEtOWU3ZS00ODQyLWFhYTgtNGYxM2ZkM2Q2YjE3XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestVisualEffects,
                        bestFilmEditing,
                        bestPicture,
                        bestSound
                )
        );




        // WEAPONS
        criarFilme(
                "Amy Madigan",
                "A Hora do Mal",
                "https://m.media-amazon.com/images/M/MV5BM2UyYWQ0OTMtZjU2OC00MzE3LTgxYTAtZmQ2YzM4NTI4OGZjXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(bestActressInASupportingRole)
        );

        // SONG SUNG BLUE
        criarFilme(
                "Kate Hudson",
                "Song Sung Blue",
                "https://m.media-amazon.com/images/M/MV5BMTA1NTk0MjMyOTFeQTJeQWpwZ15BbWU3MDA4NzEzMTM@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActressInALeadingRole)
        );

        // Se Eu Tivesse Pernas, Eu Te Chutaria
        criarFilme(
                "Rose Byrne",
                "Se Eu Tivesse Pernas, Eu Te Chutaria",
                "https://m.media-amazon.com/images/M/MV5BMTc0MDA2Njc0OF5BMl5BanBnXkFtZTcwNzc3NDU3Mw@@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(bestActressInALeadingRole)
        );


        // SONHOS DE TREM
        criarFilme(
                "Sonhos de Trem",
                "Clint Bentley",
                "https://m.media-amazon.com/images/M/MV5BZDkzNzkyNTktYzNhMi00YjhiLWI5YmUtZjliMTY0ZDc5NmMyXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestCinematography,
                        bestPicture,
                        bestWritingAdaptedScreenplay
                )
        );
        // SIRAT
        criarFilme(
                "Sirāt",
                "Oliver Laxe",
                "https://s3.amazonaws.com/nightjarprod/content/uploads/sites/130/2026/01/19005449/bzBtsLi17rK4G6kDvOXfUZfAhca-scaled.jpg",
                Arrays.asList(
                        bestInternacionalFeatureFilm,
                        bestSound
                )
        );
        //APENAS UM ACIDENTE
        criarFilme(
                "Foi apenas um acidente",
                "Jafar Panahi",
                "https://m.media-amazon.com/images/M/MV5BYzAwYjkxNGMtMWU3ZS00NWQ4LWJhNjctMTgyYWIyOTUxNWYwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestWritingOriginalScreenplay,
                        bestInternacionalFeatureFilm
                )
        );
        // A Voz de Hind Rajab
        criarFilme(
                "A Voz de Hind Rajab",
                "Kaouther Ben Hania",
                "https://m.media-amazon.com/images/M/MV5BNWQ5Nzk2OTgtZmU0YS00NjAyLWJkNTItNDEzNzYyNTFhN2E3XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestInternacionalFeatureFilm
                )
        );
        // Kokuho
        criarFilme(
                "Kokuho",
                "Lee Sang-il",
                "https://m.media-amazon.com/images/M/MV5BN2RmYjdmYjUtZTM1My00ZjM1LWJkOWItODAwNDk4MDkzMDMxXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestMakeupAndHairstyling
                )
        );
        // Coração de Lutador
        criarFilme(
                "Coração de Lutador",
                "Ben Safdie",
                "https://blackcompany.com.br/wp-content/uploads/2025/09/CDL-POSTER_64x941-697x1024.png",
                Arrays.asList(
                        bestMakeupAndHairstyling
                )
        );
        // A Meia-Irmã Feia
        criarFilme(
                "A Meia-Irmã Feia",
                "Emilie Blichfeldt",
                "https://cdn.ome.lt/snZGRRcl7EUaFgDH1lhRol1AG3g=/fit-in/837x500/smart/filters:format(webp)/uploads/conteudo/fotos/A-Meia-Irma-Feia-Poster-Oficial-RGB.jpg",
                Arrays.asList(
                        bestMakeupAndHairstyling
                )
        );

        // ANIMACAO
        criarFilme(
                "ARCO",
                "Ugo Bienvenu",
                "https://br.web.img3.acsta.net/r_1280_720/img/78/d2/78d29b3bfcb044fc04b856c486e2cd5a.jpg",
                Arrays.asList(
                        bestAnimatedFeatureFilm
                )
        );
        criarFilme(
                "ELIO",
                "Adrian Molina, Domee Shi, Madeline Sharafian",
                "https://acdn-us.mitiendanube.com/stores/004/687/740/products/pos-04242-b7495e6ae3da921a3717502503518394-1024-1024.webp",
                Arrays.asList(
                        bestAnimatedFeatureFilm
                )
        );
        criarFilme(
                "Guerreiras do K-Pop",
                "Maggie Kang, Chris Appelhans",
                "https://m.media-amazon.com/images/M/MV5BNTBiYWJlMjQtOTIyMy00NTY4LWFhOWItOWZhNzc3NGMyMjc2XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestAnimatedFeatureFilm
                )
        );
        criarFilme(
                "A Pequena Amélie",
                "Mailys Vallade, Liane-Cho Han",
                "https://m.media-amazon.com/images/M/MV5BZDQ4MzRmNjktNzNkZC00MTRmLThiMjktZTg5MDMzNWY1YzQ2XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestAnimatedFeatureFilm
                )
        );
        criarFilme(
                "ZOOTOPIA 2",
                "Jared Bush, Byron Howard",
                "https://m.media-amazon.com/images/M/MV5BYjg1Mjc3MjQtMTZjNy00YWVlLWFhMWEtMWI3ZTgxYjJmNmRlXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestAnimatedFeatureFilm
                )
        );


        // Curta Animacao
        criarFilme(
                "Butterfly",
                "Florence Miailhe",
                "https://m.media-amazon.com/images/M/MV5BYTAyZDNkNDEtZTE4YS00YTM5LWI5YmUtYzNiNTQwZDEwMzY4XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestAnimatedShortFilm
                )
        );
        criarFilme(
                "Forevergreen",
                "Nathan Engelhardt, Jeremy Spears",
                "https://m.media-amazon.com/images/M/MV5BNzRmNTJhNjQtOWFiZC00OWRmLWJkZWItZDgwNGM0NGQ5MWQzXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestAnimatedShortFilm
                )
        );
        criarFilme(
                "The Girl Who Cried Pearls",
                "Maciek Szczerbowski, Chris Lavis",
                "https://m.media-amazon.com/images/M/MV5BOGZhMWIwYmEtN2MyMS00OGFjLWIxYWMtNDMyMWNkZTAyMWI3XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestAnimatedShortFilm
                )
        );
        criarFilme(
                "Retirement Plan",
                "John Kelly",
                "https://m.media-amazon.com/images/M/MV5BZjkyYjUxYWEtZDU0NC00NmRkLThjOTYtZDgyZTlhMDI1OTQ4XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestAnimatedShortFilm
                )
        );
        criarFilme(
                "The Three Sisters",
                "Konstantin Bronzit",
                "https://m.media-amazon.com/images/M/MV5BMzM3MjZlYjUtMTU3Ni00MWVjLWEzNjItODNiNDQ2NzFjYTdkXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestAnimatedShortFilm
                )
        );

        // Curta Live Action
        criarFilme(
                "Butcher's Stain",
                "Meyer Levinson-Blount",
                "https://m.media-amazon.com/images/M/MV5BYTQ2NDM4MzYtYjM5ZS00YTcyLWI2ZjktN2U4YjdmMzAxZDEzXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestLiveActionShortFilm
                )
        );
        criarFilme(
                "A Friend of Dorothy",
                "Lee Knight",
                "https://m.media-amazon.com/images/M/MV5BMTkxYmE4ODQtYzA4Zi00MmEzLWI4MmUtOGViN2VkZjczY2NlXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestLiveActionShortFilm
                )
        );
        criarFilme(
                "Jane Austen's Period Drama",
                "Julia Aks, Steve Pinder",
                "https://m.media-amazon.com/images/M/MV5BNTVmMWUwZTAtN2NiNC00NmMyLTlhY2YtODMzMWMxZTU1YmY2XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestLiveActionShortFilm
                )
        );
        criarFilme(
                "The Singers",
                "Sam A. Davis",
                "https://m.media-amazon.com/images/M/MV5BNWE3YjQ1ZGYtNjYyZC00ZmI0LWFjNDAtZWZiZWIxNzBlMzBkXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestLiveActionShortFilm
                )
        );
        criarFilme(
                "Two People Exchanging Saliva",
                "Natalie Musteata, Alexandre Singh",
                "https://m.media-amazon.com/images/M/MV5BMmJjYzIzNjQtMDhlYy00YjEzLTg0NzgtZjFkZDcyZDA4ODg1XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestLiveActionShortFilm
                )
        );

        // Documentario

        criarFilme(
                "Alabama: Presos del Sistema",
                "Andrew Jarecki, Charlotte Kaufman",
                "https://br.web.img3.acsta.net/c_310_420/img/2c/7f/2c7f076ea2a7c4253dd27606be589903.png",
                Arrays.asList(
                        bestDocumentaryFeatureFilm
                )
        );
        criarFilme(
                "Embaixo da Luz de Neon",
                "Ryan White",
                "https://m.media-amazon.com/images/M/MV5BZDU2OTAxYTUtZjEwMS00YTdiLTkxNzgtZTdkZDcwY2I1NmY1XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestDocumentaryFeatureFilm
                )
        );
        criarFilme(
                "Cutting Through Rocks",
                "Sara Khaki, Mohammadreza Eyni",
                "https://m.media-amazon.com/images/M/MV5BYmZkZmU2OTctM2U4Ny00NmMyLWFmZTktMjk4MDJkY2QyZDdiXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestDocumentaryFeatureFilm
                )
        );
        criarFilme(
                "Mr Nobody Against Putin",
                "Pavel Talankin, David Borenstein",
                "https://s3.amazonaws.com/nightjarprod/content/uploads/sites/130/2026/01/21065218/aXCEnrqecvPXohuptN0JRgIvkDj-scaled.jpg",
                Arrays.asList(
                        bestDocumentaryFeatureFilm
                )
        );
        criarFilme(
                "A Vizinha Perfeita",
                "Geeta Gandbhir",
                "https://m.media-amazon.com/images/M/MV5BZmYxZjA5YmItMjJkYi00NzllLWJjMzgtZjFjZjBlZGQwMmRkXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestDocumentaryFeatureFilm
                )
        );

        //Curta Metragem

        criarFilme(
                "Quartos Vazios",
                "Joshua Seftel",
                "https://br.web.img3.acsta.net/c_310_420/img/65/16/6516295a5f66c6b6d798571594d9800e.png",
                Arrays.asList(
                        bestDocumentaryShortFilm
                )
        );
        criarFilme(
                "Armed Only with a Camera: The Life and Death of Brent Renaud",
                "Jon Alpert",
                "https://m.media-amazon.com/images/M/MV5BZTc2YmQzZmMtYjQxYS00ZTYzLThhMDEtNzhlMjVhMTNiYmQ1XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestDocumentaryShortFilm
                )
        );
        criarFilme(
                "Children No More: \"Were and Are Gone\"",
                "Hilla Medalia",
                "https://m.media-amazon.com/images/M/MV5BOGMwMDJlMzAtNThhMS00MTYxLWE2MGItOTE2OGNmM2UxOWFhXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestDocumentaryShortFilm
                )
        );
        criarFilme(
                "O Diabo Não Tem Descanso",
                "Christalyn Hampton, Geeta Gandbhir",
                "https://m.media-amazon.com/images/M/MV5BNGM4ZGEzMTQtN2YzMC00NjAzLWJmNjUtY2Y5OTkyM2ZmNDBjXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestDocumentaryShortFilm
                )
        );
        criarFilme(
                "Perfectly A Strangeness",
                "Alison McAlpine",
                "https://m.media-amazon.com/images/M/MV5BZmMwYTA4ODYtZjM0ZS00Y2Q2LWIyNzctOTQxZGFmZjk3ZDRiXkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestDocumentaryShortFilm
                )
        );

        // SOM ORIGINAL

        criarFilme(
                "Dear Me",
                "Diane Warren: Relentless",
                "https://www.termometrooscar.com/uploads/1/4/8/8/1488234/s118581882355374300_p2964_i5_w800.jpeg",
                Arrays.asList(
                        bestMusicOriginalSong
                )
        );
        criarFilme(
                "Golden",
                "Guerreiras do KPop",
                "https://m.media-amazon.com/images/M/MV5BNTBiYWJlMjQtOTIyMy00NTY4LWFhOWItOWZhNzc3NGMyMjc2XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestMusicOriginalSong
                )
        );
        criarFilme(
                "I Lied To You",
                "Pecadores",
                "https://m.media-amazon.com/images/M/MV5BMjVhNTQ4NjQtODgzMy00OGM1LTkzOWEtYmU2Mzg0YzUyZjA3XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestMusicOriginalSong
                )
        );
        criarFilme(
                "Sweet Dreams of Joy",
                "Viva Verdi!",
                "https://m.media-amazon.com/images/M/MV5BYTQxMjZlNmItYmNkZS00ZmI3LTljZjYtYjQxODEyYzcwZDM1XkEyXkFqcGc@._V1_.jpg",
                Arrays.asList(
                        bestMusicOriginalSong
                )
        );
        criarFilme(
                "Train Dreams",
                "Sonhos de Trem",
                "https://m.media-amazon.com/images/M/MV5BZDkzNzkyNTktYzNhMi00YjhiLWI5YmUtZjliMTY0ZDc5NmMyXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
                Arrays.asList(
                        bestMusicOriginalSong
                )
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