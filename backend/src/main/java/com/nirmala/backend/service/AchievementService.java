package com.nirmala.backend.service;

import com.nirmala.backend.model.Achievement;
import com.nirmala.backend.repository.AchievementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AchievementService {
    @Autowired
    private AchievementRepository achievementRepository;

    public Achievement createAchievement(Achievement achievement) {
        if (achievementRepository.existsByAchievementName(
                achievement.getAchievementName())) {
            throw new RuntimeException(
                    "Achievement already exists: "
                            + achievement.getAchievementName());
        }
        return achievementRepository.save(achievement);
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public Achievement getAchievementById(UUID achievementId) {
        return achievementRepository.findById(achievementId)
                .orElseThrow(() ->
                        new RuntimeException("Achievement not found"));
    }

    public void deleteAchievement(UUID achievementId) {
        if (!achievementRepository.existsById(achievementId)) {
            throw new RuntimeException("Achievement not found");
        }
        achievementRepository.deleteById(achievementId);
    }

}
