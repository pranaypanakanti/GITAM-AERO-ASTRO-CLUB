package com.GAAC.GAAC.Backend.Repository;

import com.GAAC.GAAC.Backend.DTO.response.UserMiniResponseDTO;
import com.GAAC.GAAC.Backend.ENUMS.RoleEnum;
import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import com.GAAC.GAAC.Backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    List<User> findByRole(RoleEnum role);

    @Query(value = "SELECT * FROM users u WHERE u.email ~ :regex", nativeQuery = true)
    List<User> findUsersWithValidEmail(@Param("regex") String regex);

    @Query("""
    SELECT u FROM User u
    WHERE u.team = :team
    ORDER BY
        CASE
            WHEN u.team = 'EB' AND u.position = 'PRESIDENT' THEN 1
            WHEN u.team = 'EB' AND u.position = 'VICE_PRESIDENT' THEN 2
            WHEN u.team = 'EB' AND u.position = 'SECRETARY' THEN 3
            WHEN u.position = 'LEAD' THEN 4
            ELSE 5
        END,
        u.id ASC
    """)
    List<User> findByTeam(@Param("team") TeamEnum team);

}

