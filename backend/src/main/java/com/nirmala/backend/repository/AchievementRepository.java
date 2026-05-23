package com.nirmala.backend.repository;


import com.nirmala.backend.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AchievementRepository  extends JpaRepository<Achievement, UUID> {
    Optional<Achievement> findByAchievementName(String achievementName);
    boolean existsByAchievementName(String achievementName);
}
