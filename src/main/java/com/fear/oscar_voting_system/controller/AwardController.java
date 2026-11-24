package com.fear.oscar_voting_system.controller;

import com.fear.oscar_voting_system.exception.BusinessException;
import com.fear.oscar_voting_system.exception.ResourceNotFoundException;
import com.fear.oscar_voting_system.model.UserModel;
import com.fear.oscar_voting_system.repository.NominationRepository;
import com.fear.oscar_voting_system.repository.UserRepository;
import com.fear.oscar_voting_system.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/awards")
@RequiredArgsConstructor
public class AwardController {
    private final UserRepository users;
    private final NominationRepository nominations;
    private final RankingService ranking;

    public record Tag(String id, String label, int position) {}
    public record Preference(String id) {}
    public record Grant(UUID userId) {}
    public record Awards(Map<UUID, Tag> displayed, List<Tag> available, String preference, UUID champion2025) {}

    @GetMapping
    public Awards getAwards(Authentication auth) {
        UUID viewer = UUID.fromString(auth.getName());
        var earned = earnedTags();
        Map<UUID, Tag> displayed = new HashMap<>();
        String preference = null;
        UUID champion = null;
        for (var user : users.findAll()) {
            if (Integer.valueOf(2025).equals(user.getManualChampionYear())) champion = user.getId();
            var options = earned.getOrDefault(user.getId(), List.of());
            String selected = user.getDisplayedAward();
            if (viewer.equals(user.getId())) preference = selected;
            if (selected == null && !options.isEmpty()) displayed.put(user.getId(), options.getFirst());
            else options.stream().filter(tag -> tag.id().equals(selected)).findFirst()
                .ifPresent(tag -> displayed.put(user.getId(), tag));
        }
        return new Awards(displayed, earned.getOrDefault(viewer, List.of()), preference, champion);
    }

    @PutMapping("/preference")
    public void select(@RequestBody Preference preference, Authentication auth) {
        var user = find(UUID.fromString(auth.getName()));
        String id = preference.id();
        if (id == null || (!id.isEmpty() && earnedTags().getOrDefault(user.getId(), List.of())
                .stream().noneMatch(tag -> tag.id().equals(id))))
            throw new BusinessException("Escolhe uma tag que tu ganhou.");
        user.setDisplayedAward(id);
        users.save(user);
    }

    @PutMapping("/champion-2025")
    @Transactional
    public void grant(@RequestBody Grant grant) {
        if (grant.userId() == null) throw new BusinessException("Escolhe um participante.");
        var recipient = find(grant.userId());
        for (var user : users.findAll()) {
            if (Integer.valueOf(2025).equals(user.getManualChampionYear())) {
                if (user.getId().equals(recipient.getId())) return;
                user.setManualChampionYear(null);
            }
        }
        users.flush();
        recipient.setManualChampionYear(2025);
    }

    private UserModel find(UUID id) {
        return users.findById(id).filter(user -> user.getDeletedAt() == null)
            .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado."));
    }

    private Map<UUID, List<Tag>> earnedTags() {
        Map<UUID, List<Tag>> earned = new HashMap<>();
        for (int year : nominations.completedAwardYears()) {
            var scores = ranking.rankingScore(year);
            int position = 1;
            for (int i = 0; i < scores.size(); i++) {
                var score = scores.get(i);
                if (i > 0 && score.score() != scores.get(i - 1).score()) position = i + 1;
                if (position > 3 || score.score() <= 0) break;
                add(earned, score.id(), year, position);
            }
        }
        for (var user : users.findAll()) {
            if (user.getDeletedAt() == null && Integer.valueOf(2025).equals(user.getManualChampionYear()))
                add(earned, user.getId(), 2025, 1);
        }
        return earned;
    }

    private void add(Map<UUID, List<Tag>> earned, UUID userId, int year, int position) {
        String title = switch (position) {
            case 1 -> "OCG Champion";
            case 2 -> "OCG Runner-up";
            default -> "OCG Third Place";
        };
        earned.computeIfAbsent(userId, ignored -> new ArrayList<>())
            .add(new Tag(year + "-" + position, title + " " + year, position));
    }
}
