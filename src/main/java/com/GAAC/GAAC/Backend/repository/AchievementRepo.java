package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.Achievement;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AchievementRepo extends JpaRepository<Achievement, UUID> {
    Optional<Achievement> findByTitle(String title);
    List<Achievement> findByTeam(TeamEnum team);
}
