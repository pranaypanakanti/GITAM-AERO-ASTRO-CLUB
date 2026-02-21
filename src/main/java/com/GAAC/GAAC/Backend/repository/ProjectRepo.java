package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.Project;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepo extends JpaRepository<Project, UUID> {
    Optional<Project> findByTitle(String title);
    List<Project> findByTeam(TeamEnum team);
}
