package com.fear.oscar_voting_system.service;

import com.fear.oscar_voting_system.dto.ResponseUserScoreDTO;
import com.fear.oscar_voting_system.dto.ShareCardDTO;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareCardService {
    private final UserRepository users;
    private final VoteRepository votes;
    private final RankingService ranking;

    public ShareCardDTO getCard(UUID userId, int year) {
        var user = users.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        var predictions = votes.findByUser_IdAndYear(userId, year);
        int rank = 0;
        int points = 0;
        var scores = ranking.rankingScore(year);
        int position = 1;
        for (int index = 0; index < scores.size(); index++) {
            ResponseUserScoreDTO score = scores.get(index);
            if (index > 0 && score.score() != scores.get(index - 1).score()) position = index + 1;
            if (score.id().equals(userId)) {
                points = score.score();
                rank = position;
                break;
            }
        }
        long correct = predictions.stream().filter(v -> Boolean.TRUE.equals(v.getNomination().getIsWinner())).count();
        return new ShareCardDTO(userId, user.getUsername(), user.getImageUrl(), year, rank, points, predictions.size(), correct);
    }
}
