package com.GAAC.GAAC.Backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class SendGridEmailService {

    private static final Logger log = LoggerFactory.getLogger(SendGridEmailService.class);

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String sendGridApiKey;
    private final String fromEmail;
    private final String fromName;

    public SendGridEmailService(
            @Value("${SENDGRID_API_KEY}") String sendGridApiKey,
            @Value("${SENDGRID_FROM_EMAIL}") String fromEmail,
            @Value("${SENDGRID_FROM_NAME:GAAC Club}") String fromName) {
        this.sendGridApiKey = sendGridApiKey.trim();
        this.fromEmail = fromEmail.trim();
        this.fromName = fromName.trim();

        this.webClient = WebClient.builder()
                .baseUrl("https://api.sendgrid.com/v3")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends an email using the SendGrid Web API v3.
     *
     * @param to      recipient email address
     * @param subject email subject
     * @param body    plain text body
     * @throws RuntimeException if the request fails (invalid API key, unverified sender, network error)
     */
    public void sendEmail(String to, String subject, String body) {
        log.info("Preparing to send email via SendGrid to: {}", to);

        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            ArrayNode personalizations = objectMapper.createArrayNode();
            ObjectNode personalization = objectMapper.createObjectNode();
            ArrayNode toArray = objectMapper.createArrayNode();
            ObjectNode toEmail = objectMapper.createObjectNode();
            toEmail.put("email", to);
            toArray.add(toEmail);
            personalization.set("to", toArray);
            personalizations.add(personalization);
            requestBody.set("personalizations", personalizations);
            ObjectNode from = objectMapper.createObjectNode();
            from.put("email", this.fromEmail);
            from.put("name", this.fromName);
            requestBody.set("from", from);
            requestBody.put("subject", subject);
            ArrayNode content = objectMapper.createArrayNode();
            ObjectNode textContent = objectMapper.createObjectNode();
            textContent.put("type", "text/plain");
            textContent.put("value", body);
            content.add(textContent);
            requestBody.set("content", content);

            String jsonPayload = objectMapper.writeValueAsString(requestBody);

            log.debug("SendGrid request payload: {}", jsonPayload);

            webClient.post()
                    .uri("/mail/send")
                    .header("Authorization", "Bearer " + this.sendGridApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(jsonPayload)
                    .retrieve()
                    .toBodilessEntity()
                    .block();  // synchronous – fine for low‑volume club usage

            log.info("✅ Email sent successfully to {}", to);

        } catch (WebClientResponseException e) {
            log.error("❌ SendGrid API error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new RuntimeException("SendGrid API error: " + e.getStatusCode(), e);
        } catch (Exception e) {
            log.error("❌ Failed to send email via SendGrid API", e);
            throw new RuntimeException("Failed to send email via SendGrid API", e);
        }
    }
}