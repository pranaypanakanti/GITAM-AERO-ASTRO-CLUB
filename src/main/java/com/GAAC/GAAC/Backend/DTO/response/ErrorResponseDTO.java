package com.GAAC.GAAC.Backend.DTO.response;

import java.util.Map;

public record ErrorResponseDTO(
        String code,
        String message,
        Map<String, String> details
) {}
