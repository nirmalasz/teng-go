package com.nirmala.backend.controller;

import com.nirmala.backend.model.Achievement;
import com.nirmala.backend.service.AchievementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin(origins = "*")
public class AchievementController {
    @Autowired
    private AchievementService achievementService;

    @PostMapping
    public ResponseEntity<?> createAchievement(@RequestBody Achievement achievement) {
        try {
            return ResponseEntity.ok(
                    achievementService.createAchievement(achievement));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Achievement>> getAllAchievements() {
        return ResponseEntity.ok(
                achievementService.getAllAchievements());
    }

    @GetMapping("/{achievementId}")
    public ResponseEntity<?> getAchievementById(@PathVariable UUID achievementId) {
        try {
            return ResponseEntity.ok(
                    achievementService.getAchievementById(achievementId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{achievementId}")
    public ResponseEntity<?> deleteAchievement(@PathVariable UUID achievementId) {

        try {
            achievementService.deleteAchievement(achievementId);
            return ResponseEntity.ok("Achievement deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
