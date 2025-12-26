package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogRepo extends JpaRepository<Blog, UUID> {
    Optional<Blog> findByTitle(String title);
    List<Blog> findByTeam(TeamEnum team);
}
