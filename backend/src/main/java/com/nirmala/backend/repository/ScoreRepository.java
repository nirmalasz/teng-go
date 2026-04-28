package com.nirmala.backend.repository;

import com.nirmala.backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {

    List<Score> findByPlayerId(UUID playerId);
    List<Score> findByPlayerIdOrderByValueDesc(UUID playerId);
    List<Score> findByValueGreaterThan(Integer minValue);
    List<Score> findAllByOrderByCreatedAtDesc();

    @Query("SELECT s FROM Score s ORDER BY s.value DESC")
    List<Score> findTopScores(Integer limit);
    List<Score> findHighestScoreByPlayerId(@Param("playerId") UUID playerId);

    @Query("SELECT SUM(s.coinsCollected) FROM Score s WHERE s.playerId =:playerId ")
    Integer getTotalCoinsByPlayerId(@Param("playerId") UUID playerId);

    @Query("SELECT s.levelReached FROM Score s WHERE s.playerId =:playerId")
    Integer getLevelReachedbyPlayerId(@Param("playerId") UUID playerId);
}
