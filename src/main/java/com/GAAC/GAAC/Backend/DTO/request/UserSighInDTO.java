package com.GAAC.GAAC.Backend.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSighInDTO {
    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@(gitam\\.in|gitam\\.edu|student\\.edu)$",
            message = "Invalid email format, use collage email id only."
    )
    private String email;
    private String password;
}
