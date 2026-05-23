package com.nirmala.backend.service;

import com.nirmala.backend.model.Player;
import com.nirmala.backend.repository.PlayerRepository;
import com.nirmala.backend.model.Score;
import com.nirmala.backend.repository.ScoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Transactional
    public Score createScore(Score score){
        Player existingPlayer = playerRepository.findById(score.getPlayerId())
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + score.getPlayerId()));

        Score savedScore = scoreRepository.save(score);

        playerService.updatePlayerStats(score.getPlayerId(), score.getValue(),
                score.getCoinsCollected(), score.getLevelReached());

        return savedScore;
    }

    public Optional<Score> getScoreById(UUID scoreId){
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores(){
        return scoreRepository.findAll();
    }

    public List<Score> getScoresByPlayerId(UUID playerId){
        return scoreRepository.findByPlayerId(playerId);
    }

    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId){
        return scoreRepository.findByPlayerIdOrderByValueDesc(playerId);
    }

    public List<Score> getLeaderboard(int limit){
        return scoreRepository.findTopScores(limit);
    }

    public Optional<Score> getHighestScoreByPlayerId(UUID playerId){
        List<Score> scores = scoreRepository.findHighestScoreByPlayerId(playerId);

        if (scores.isEmpty()){
            return Optional.empty();
        }
        return  Optional.of(scores.get(0));
    }

    public List<Score> getScoreAboveValue(Integer minValue){
        return scoreRepository.findByValueGreaterThan(minValue);
    }

    public List<Score> getRecentScores(){
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public Integer getTotalCoinsByPlayerId(UUID playerId){
        Integer total = scoreRepository.getTotalCoinsByPlayerId(playerId);
        if (total == null){
            return 0;
        } else {
            return total;
        }
    }

    public Integer getLevelReachedbyPlayerId(UUID playerId){
        Integer total = scoreRepository.getLevelReachedbyPlayerId(playerId);
        if (total == null){
            return 0;
        } else{
            return total;
        }
    }

    public Score updateScore(UUID scoreId, Score updatedScore){
        Score existingScore = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found with ID: " +scoreId));
        if (updatedScore.getValue() != 0){
            existingScore.setValue(updatedScore.getValue());
        }
        if (updatedScore.getCoinsCollected() != 0 ){
            existingScore.setCoinsCollected(updatedScore.getCoinsCollected());
        }
        if (updatedScore.getLevelReached()!= 0){
            existingScore.setLevelReached(updatedScore.getLevelReached());
        }
        return scoreRepository.save(existingScore);
    }


    public void deleteScore(UUID scoreId){
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(()-> new RuntimeException("Score not found with ID: " + scoreId));
        scoreRepository.delete(score);
    }

    public void deleteScoresByPlayerId(UUID playerId){
        List<Score> score = scoreRepository.findByPlayerId(playerId);

        scoreRepository.deleteAll(score);
    }

}
