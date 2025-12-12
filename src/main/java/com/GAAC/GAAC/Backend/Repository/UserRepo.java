package com.GAAC.GAAC.Backend.Repository;

import com.GAAC.GAAC.Backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    void deleteByEmail(String email);
//    List<User> findByTeamIgnoreCase(String team);
//    List<User> findByRoleIgnoreCase(String team);
//    List<User> findUserByRecruitmentStatusIgnoreCase(String team);

    @Query(value = "SELECT * FROM users u WHERE u.email ~ :regex", nativeQuery = true)
    List<User> findUsersWithValidEmail(@Param("regex") String regex);
}
