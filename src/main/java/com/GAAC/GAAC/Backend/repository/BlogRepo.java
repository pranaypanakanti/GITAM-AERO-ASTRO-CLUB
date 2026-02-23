package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogRepo extends JpaRepository<Blog, UUID> {
    Optional<Blog> findByTitle(String title);
    List<Blog> findByTeam(TeamEnum team);

    @Query("""
        SELECT b FROM Blog b
        WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(b.content) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY b.createdAt DESC
        """)
    List<Blog> searchBlogs(@Param("query") String query);
}
