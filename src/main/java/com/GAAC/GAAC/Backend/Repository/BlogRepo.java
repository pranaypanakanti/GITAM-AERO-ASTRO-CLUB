package com.GAAC.GAAC.Backend.Repository;

import com.GAAC.GAAC.Backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogRepo extends JpaRepository<User, UUID> {
}
