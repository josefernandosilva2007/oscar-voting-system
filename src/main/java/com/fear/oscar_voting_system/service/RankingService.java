package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.model.CategoryModel;
import com.fear.oscar_voting_system.model.MovieModel;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.model.VoteModel;
import com.fear.oscar_voting_system.repository.CategoryRepository;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RankingService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;




    public List<ResponseUserScoreDTO> rankingScore(){
        List<ResponseUserScoreDTO> ranking = new ArrayList<>();
        List<UserModel> allUsers = userRepository.findAll();
        List<VoteModel> allVotes = voteRepository.findAll();

        Map<UserModel, List<VoteModel>> voteByUser =
                allVotes.stream()
                .collect(Collectors.groupingBy(VoteModel::getUser));

        for(UserModel user : allUsers){
            int score = 0;
            List<VoteModel> voteForThisUser =
                    voteByUser.getOrDefault(user, Collections.emptyList());
            for (VoteModel vote : voteForThisUser){
                CategoryModel category = vote.getCategory();
                MovieModel movieWinning = category.getMovieWinning();
                if(movieWinning!= null && vote.getMovie().getId().equals(movieWinning.getId()))
                    score++;
            }
            ranking.add(new ResponseUserScoreDTO(user.getId(),user.getUsername(), score,user.getProfilePictureUrl()));
        }
        ranking.sort(Comparator.comparingInt(ResponseUserScoreDTO::score).reversed());
        return ranking;
    }

}
