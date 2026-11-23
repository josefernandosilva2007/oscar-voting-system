package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RankingService {
    private final VoteRepository voteRepository;
    @Value("${oscar.auth.admin-username:}")
    private String adminUsername = "";

    public List<ResponseUserScoreDTO> rankingScore(){
        return withoutAdmin(voteRepository.getRanking());
    }

    public List<ResponseUserScoreDTO> rankingScore(int year) {
        return withoutAdmin(voteRepository.getRankingByYear(year));
    }

    private List<ResponseUserScoreDTO> withoutAdmin(List<ResponseUserScoreDTO> ranking) {
        if (adminUsername.isBlank()) return ranking;
        return ranking.stream().filter(entry -> !adminUsername.equalsIgnoreCase(entry.username())).toList();
    }
}
