package com.GAAC.GAAC.Backend.repository;

import com.GAAC.GAAC.Backend.model.MailContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MailContentRepo extends JpaRepository<MailContent, UUID> {
    Optional<MailContent> findByTitle(String title);
}
