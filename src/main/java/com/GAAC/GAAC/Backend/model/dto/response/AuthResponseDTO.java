package com.GAAC.GAAC.Backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String name;
    private String role;

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}