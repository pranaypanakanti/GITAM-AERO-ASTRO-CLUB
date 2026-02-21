package com.GAAC.GAAC.Backend.model.dto.response;

import com.GAAC.GAAC.Backend.model.enums.PriorityEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ProjectResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private TeamEnum team;
    private Instant createdAt;
    private PriorityEnum priority;
}
