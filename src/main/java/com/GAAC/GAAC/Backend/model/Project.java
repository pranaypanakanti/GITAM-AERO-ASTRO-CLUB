package com.GAAC.GAAC.Backend.model;

import com.GAAC.GAAC.Backend.model.enums.PriorityEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "text", nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private TeamEnum team;
    @Column(nullable=false)
    private Instant createdAt = Instant.now();
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;
}
