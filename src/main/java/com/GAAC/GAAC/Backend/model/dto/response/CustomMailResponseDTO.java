package com.GAAC.GAAC.Backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomMailResponseDTO {

    private int totalRecipients;
    private int successfulSends;
    private int failedSends;

    private List<String> successfulEmails;
    private Map<String, String> failedEmailsWithReason;

    private String message;
}