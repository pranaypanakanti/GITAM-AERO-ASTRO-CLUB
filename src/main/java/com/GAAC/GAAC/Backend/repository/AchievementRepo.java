package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.Achievement;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AchievementRepo extends JpaRepository<Achievement, UUID> {
    Optional<Achievement> findByTitle(String title);

    @Query("SELECT a FROM Achievement a ORDER BY " +
            "CASE a.priority " +
            "   WHEN 'HIGH' THEN 1 " +
            "   WHEN 'MEDIUM' THEN 2 " +
            "   WHEN 'LOW' THEN 3 " +
            "END")
    List<Achievement> findAllOrderedByPriority();

    @Query("""
        SELECT a FROM Achievement a
        WHERE a.team = :team
        ORDER BY
            CASE a.priority
                WHEN 'HIGH' THEN 1
                WHEN 'MEDIUM' THEN 2
                WHEN 'LOW' THEN 3
                ELSE 4
            END,
            a.id ASC
        """)
    List<Achievement> findByTeamOrderByPriority(@Param("team") TeamEnum team);

    @Query("""
        SELECT a FROM Achievement a
        WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.content) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY 
            CASE a.priority
                WHEN 'HIGH' THEN 1
                WHEN 'MEDIUM' THEN 2
                WHEN 'LOW' THEN 3
            END,
            a.createdAt DESC
        """)
    List<Achievement> searchAchievements(@Param("query") String query);
}
