package com.GAAC.GAAC.Backend.model;

import com.GAAC.GAAC.Backend.model.enums.MailContentEnum;
import com.GAAC.GAAC.Backend.model.enums.PriorityEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
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
public class MailContent {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(nullable = false)
    private MailContentEnum title;
    @Column(columnDefinition = "text", nullable = false)
    private String subject;
    @Column(columnDefinition = "text", nullable = false)
    private String body;
}
