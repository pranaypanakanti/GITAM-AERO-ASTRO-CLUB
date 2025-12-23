package com.GAAC.GAAC.Backend.DTO.request;

import com.GAAC.GAAC.Backend.ENUMS.TeamEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsightDetailsDTO {
    @NotBlank
    private String title;
    private String content;
}
