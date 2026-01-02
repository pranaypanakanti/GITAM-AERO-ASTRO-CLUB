package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.enums.RecruitmentStatusEnum;
import com.GAAC.GAAC.Backend.model.enums.RoleEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.GAAC.GAAC.Backend.model.User;
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
    List<User> findByRecruitmentStatus(RecruitmentStatusEnum statusName);

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

