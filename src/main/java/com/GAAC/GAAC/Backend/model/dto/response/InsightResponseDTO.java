package com.GAAC.GAAC.Backend.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class InsightResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private Instant createdAt;
    private String authorName;
}
