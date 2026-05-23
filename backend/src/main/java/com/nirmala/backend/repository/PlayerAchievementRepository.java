package com.nirmala.backend.repository;
import com.nirmala.backend.model.PlayerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerAchievementRepository  extends JpaRepository<PlayerAchievement, UUID> {
    List<PlayerAchievement> findByPlayerId(UUID playerId);
    List<PlayerAchievement> findByAchievementId(UUID achievementId);
    boolean existsByPlayerIdAndAchievementId(UUID playerId, UUID achievementId);
}
