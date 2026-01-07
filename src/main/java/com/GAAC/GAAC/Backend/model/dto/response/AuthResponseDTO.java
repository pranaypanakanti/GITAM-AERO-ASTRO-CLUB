package com.GAAC.GAAC.Backend.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private  String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }
}
