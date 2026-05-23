package com.nirmala.backend.controller;

import com.nirmala.backend.model.PlayerAchievement;
import com.nirmala.backend.service.PlayerAchievementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/player-achievements")
@CrossOrigin(origins = "*")
public class PlayerAchievementController {
    @Autowired
    private PlayerAchievementService playerAchievementService;

    @PostMapping
    public ResponseEntity<?> unlockAchievement(@RequestBody PlayerAchievement playerAchievement) {
        try {
            return ResponseEntity.ok(
                    playerAchievementService
                            .unlockAchievement(playerAchievement));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<PlayerAchievement>>
    getAchievementsByPlayerId(@PathVariable UUID playerId) {
        return ResponseEntity.ok(
                playerAchievementService
                        .getAchievementsByPlayerId(playerId));
    }

    @GetMapping("/achievement/{achievementId}")
    public ResponseEntity<List<PlayerAchievement>>
    getPlayersByAchievementId(@PathVariable UUID achievementId) {
        return ResponseEntity.ok(
                playerAchievementService
                        .getPlayersByAchievementId(achievementId));
    }
}
