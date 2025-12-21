package com.GAAC.GAAC.Backend.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDetailsDTO {
    @NotBlank
    private String title;
    private String content;
}
