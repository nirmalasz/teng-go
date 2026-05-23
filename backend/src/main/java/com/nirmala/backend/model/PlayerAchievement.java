package com.nirmala.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "player_achievements")
public class PlayerAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "player_achievement_id")
    private UUID playerAchievementId;

    @Column(name = "player_id", nullable = false)
    private UUID playerId;

    @Column(name = "achievement_id", nullable = false)
    private UUID achievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", insertable = false, updatable = false)
    private Achievement achievement;

    @CreationTimestamp
    @Column(name = "unlocked_at", updatable = false)
    private LocalDateTime unlockedAt;

    // default constructor
    public PlayerAchievement() {}

    public PlayerAchievement(UUID playerId, UUID achievementId) {
        this.playerId = playerId;
        this.achievementId = achievementId;
    }

    // getters setters

    public UUID getPlayerAchievementId() {
        return playerAchievementId;
    }

    public void setPlayerAchievementId(UUID playerAchievementId) {
        this.playerAchievementId = playerAchievementId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
    }

    public Player getPlayer() {
        return player;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public LocalDateTime getUnlockedAt() {
        return unlockedAt;
    }
}
