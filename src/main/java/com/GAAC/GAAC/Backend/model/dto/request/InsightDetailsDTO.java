package com.GAAC.GAAC.Backend.model.dto.request;

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
