package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RankingService {
    private final VoteRepository voteRepository;

    public List<ResponseUserScoreDTO> rankingScore(){
        return voteRepository.getRanking();
    }

}
