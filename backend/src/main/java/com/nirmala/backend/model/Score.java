package com.nirmala.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "score_id")
    private UUID scoreId;

    @Column(name = "player_id", nullable = false)
    private UUID playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    private Player player;

    @Column(nullable = false)
    private Integer value;

    @Column(name = "coins_collected")
    private Integer coinsCollected = 0;

    @Column(name = "level_reached")
    private Integer levelReached = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    //default constructor
    public Score(){}

    //constructor w required fields
    public Score(UUID playerId, Integer value, Integer coinsCollected, Integer levelReached){
        this.playerId = playerId;
        this.value = value;
        this.coinsCollected = coinsCollected;
        this.levelReached = levelReached;
    }

    //getter n setters


    public UUID getScoreId() {
        return scoreId;
    }

    public void setScoreId(UUID scoreId) {
        this.scoreId = scoreId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getCoinsCollected() {
        return coinsCollected;
    }

    public void setCoinsCollected(Integer coinsCollected) {
        this.coinsCollected = coinsCollected;
    }

    public Integer getLevelReached() {
        return levelReached;
    }

    public void setLevelReached(Integer levelReached) {
        this.levelReached = levelReached;
    }
}
