package com.nirmala.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "achievement_id")
    private UUID achievementId;

    @Column(name = "achievement_name", nullable = false, unique = true)
    private String achievementName;

    @Column(name = "description")
    private String description;

    @Column(name = "reward_coins")
    private Integer rewardCoins = 0;

    // default constructor
    public Achievement() {}

    public Achievement(String achievementName, String description, Integer rewardCoins) {
        this.achievementName = achievementName;
        this.description = description;
        this.rewardCoins = rewardCoins;
    }

    // getters setters

    public UUID getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRewardCoins() {
        return rewardCoins;
    }

    public void setRewardCoins(Integer rewardCoins) {
        this.rewardCoins = rewardCoins;
    }
}
