package com.GAAC.GAAC.Backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(columnDefinition = "uuid")
    private String id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", columnDefinition = "text", nullable = false)
    private String content;
    @Column(name="author_id", columnDefinition = "uuid")
    private String authorId;
    @Column(name="created_at", nullable=false)
    private Instant createdAt = Instant.now();
}
