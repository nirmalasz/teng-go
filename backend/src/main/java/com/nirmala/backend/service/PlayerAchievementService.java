package com.nirmala.backend.service;

import com.nirmala.backend.model.PlayerAchievement;
import com.nirmala.backend.repository.PlayerAchievementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerAchievementService {
    @Autowired
    private PlayerAchievementRepository playerAchievementRepository;

    public PlayerAchievement unlockAchievement(PlayerAchievement playerAchievement) {
        boolean alreadyUnlocked =
                playerAchievementRepository
                        .existsByPlayerIdAndAchievementId(
                                playerAchievement.getPlayerId(),
                                playerAchievement.getAchievementId()
                        );
        if (alreadyUnlocked) {
            throw new RuntimeException("Achievement already unlocked");
        }
        return playerAchievementRepository.save(playerAchievement);
    }

    public List<PlayerAchievement> getAchievementsByPlayerId(UUID playerId) {
        return playerAchievementRepository.findByPlayerId(playerId);
    }

    public List<PlayerAchievement> getPlayersByAchievementId(UUID achievementId) {
        return playerAchievementRepository.findByAchievementId(achievementId);
    }
}
