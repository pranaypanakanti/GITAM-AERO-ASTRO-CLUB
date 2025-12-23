package com.GAAC.GAAC.Backend.Repository;
import com.GAAC.GAAC.Backend.Model.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsightRepo extends JpaRepository<Insight, UUID> {
}
