package com.GAAC.GAAC.Backend.Repository;

import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import com.GAAC.GAAC.Backend.Model.Blog;
import com.GAAC.GAAC.Backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogRepo extends JpaRepository<Blog, UUID> {
    Optional<Blog> findByTitle(String title);
    List<Blog> findByTeam(TeamEnum team);
}
