package com.GAAC.GAAC.Backend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CustomMailDTO {

    @NotEmpty(message = "Recipient list cannot be empty")
    private List<@Email(message = "Invalid email format") String> emails;

    @NotBlank(message = "Subject is required")
    @Size(max = 200, message = "Subject cannot exceed 200 characters")
    private String subject;

    @NotBlank(message = "Body is required")
    @Size(max = 10000, message = "Body cannot exceed 10000 characters")
    private String body;

}