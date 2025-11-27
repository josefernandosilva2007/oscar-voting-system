package com.fear.oscar_voting_system.controller;


import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RankingController {
    @Autowired
    RankingService rankingService;

    @GetMapping("/ranking")
    public List<ResponseUserScoreDTO> getRanking(){
        return rankingService.rankingScore();
    }
}
