package com.GAAC.GAAC.Backend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@(gitam\\.in|gitam\\.edu|student\\.edu)$",
            message = "Invalid email format, use collage email id only."
    )
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}