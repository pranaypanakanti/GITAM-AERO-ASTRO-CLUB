package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.Project;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepo extends JpaRepository<Project, UUID> {
    Optional<Project> findByTitle(String title);

    @Query("SELECT a FROM Project a ORDER BY " +
            "CASE a.priority " +
            "   WHEN 'HIGH' THEN 1 " +
            "   WHEN 'MEDIUM' THEN 2 " +
            "   WHEN 'LOW' THEN 3 " +
            "END")
    List<Project> findAllOrderedByPriority();

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
    List<Project> findByTeamOrderByPriority(@Param("team") TeamEnum team);

    @Query("""
        SELECT p FROM Project p
        WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY p.createdAt DESC
        """)
    List<Project> searchProjects(@Param("query") String query);
}
