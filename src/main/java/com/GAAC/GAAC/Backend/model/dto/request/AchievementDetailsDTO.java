package com.GAAC.GAAC.Backend.model.dto.request;

import com.GAAC.GAAC.Backend.model.enums.PriorityEnum;
import com.GAAC.GAAC.Backend.model.enums.TeamEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchievementDetailsDTO {
    @NotBlank
    private String title;
    private String content;
    private TeamEnum team;
    private String imageUrl;
    private PriorityEnum priority;
}
