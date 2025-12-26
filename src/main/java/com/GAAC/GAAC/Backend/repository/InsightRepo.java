package com.GAAC.GAAC.Backend.repository;
import com.GAAC.GAAC.Backend.model.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InsightRepo extends JpaRepository<Insight, UUID> {
    Optional<Insight> findByTitle(String insight);
}
