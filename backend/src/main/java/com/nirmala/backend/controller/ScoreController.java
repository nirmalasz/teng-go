package com.nirmala.backend.controller;

import com.nirmala.backend.model.Player;
import com.nirmala.backend.model.Score;
import com.nirmala.backend.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @PostMapping
    public ResponseEntity<?> createScore(@RequestBody Score score){
        try {
            Score newScore = scoreService.createScore(score);
            return ResponseEntity.status(HttpStatus.CREATED).body(newScore);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Gagal membuat score baru: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?>  getAllScores(){
        List<Score> scores = scoreService.getAllScores();
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/{scoreId}")
    public ResponseEntity<?> getScoreById(@PathVariable UUID scoreId){
        Optional<Score> score = scoreService.getScoreById(scoreId);

        if(score.isPresent()){
            return ResponseEntity.ok(score.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{ID score tidak ditemukan: " + scoreId + "\"}");
        }
    }

    @PutMapping("/{scoreId}")
    public ResponseEntity<?> updateScore(@PathVariable UUID scoreId, @RequestBody Score score){
        try {
            Score updatedScore = scoreService.updateScore(scoreId, score);
            return ResponseEntity.ok(updatedScore);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gagal update score: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{scoreId}")
    public ResponseEntity<?> deleteScore(@PathVariable UUID scoreId){
        try {
            scoreService.deleteScore(scoreId);
            return ResponseEntity.ok("Score berhasil dihapus");
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Score gagal dihapus: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Score>> getScoresByPlayerId(@PathVariable UUID playerId){
        List<Score> scores = scoreService.getScoresByPlayerId(playerId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/player/{playerId}/ordered")
    public ResponseEntity<List<Score>> getScoresByPlayerIdOrdered(@PathVariable UUID playerId){
        List<Score> scores = scoreService.getScoresByPlayerIdOrderByValue(playerId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<Score>> getLeaderboard(@RequestParam(defaultValue = "10") int limit){
        List<Score> scores = scoreService.getLeaderboard(limit);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/player/{playerId}/highest")
    public ResponseEntity<?> getHighestScoreByPlayerId(@PathVariable UUID playerId){
        Optional<Score> highestScore = scoreService.getHighestScoreByPlayerId(playerId);
        if (highestScore.isPresent()){
            return ResponseEntity.ok(highestScore);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No scores found: " + playerId);
        }
    }

    @GetMapping("/above/{minValue}")
    public ResponseEntity<List<Score>> getScoresAboveValue(@PathVariable Integer minValue){
        List<Score> scores = scoreService.getScoreAboveValue(minValue);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Score>> getRecentScores(){
        List<Score> scores = scoreService.getRecentScores();
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/player/{playerId}/total-coins")
    public ResponseEntity<?> getTotalCoinsByPlayerId(@PathVariable UUID playerId){
        Integer coins = scoreService.getTotalCoinsByPlayerId(playerId);
        return ResponseEntity.ok(coins);
    }

    @GetMapping("/player/{playerId}/level-reached")
    public ResponseEntity<?> getLevelReachedByPlayerId(@PathVariable UUID playerId){
        Integer distance = scoreService.getLevelReachedbyPlayerId(playerId);
        return ResponseEntity.ok(distance);
    }

    @DeleteMapping("/player/{playerId}")
    public ResponseEntity<?> deleteScoresByPlayerId(@PathVariable UUID playerId){
        try {
            scoreService.deleteScoresByPlayerId(playerId);
            return ResponseEntity.ok("{\"message\": \"Score deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
