package com.GAAC.GAAC.Backend.service;

import com.GAAC.GAAC.Backend.model.MailContent;
import com.GAAC.GAAC.Backend.model.dto.request.CustomMailDTO;
import com.GAAC.GAAC.Backend.model.dto.response.CustomMailResponseDTO;
import com.GAAC.GAAC.Backend.model.enums.MailContentEnum;
import com.GAAC.GAAC.Backend.repository.MailContentRepo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailContentRepo mailContentRepo;

    @Autowired
    private SendGridEmailService sendGridEmailService;

    public void sendEmail(String to, String subject, String body){
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateMailContentById(@Valid MailContentEnum mailContentTitle, @Valid MailContent newMailContent) {
        try{
            MailContent oldContent =  mailContentRepo.findByTitle(mailContentTitle).orElse(null);
            if(oldContent == null) throw  new RuntimeException("Mail title not found");
            oldContent.setBody(newMailContent.getBody() != null && !newMailContent.getBody().isEmpty() ? newMailContent.getBody() : oldContent.getBody());
            oldContent.setSubject(newMailContent.getSubject() != null && !newMailContent.getSubject().isEmpty() ? newMailContent.getSubject() : oldContent.getSubject());
            mailContentRepo.save(oldContent);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveMailContent(@Valid MailContent mailContent) {
        MailContent newMailContent = new MailContent();
        newMailContent.setTitle(mailContent.getTitle());
        newMailContent.setSubject(mailContent.getSubject());
        newMailContent.setBody(mailContent.getBody());
        mailContentRepo.save(newMailContent);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public CustomMailResponseDTO sendEmailToAddresses(CustomMailDTO request) {
        List<String> successfulEmails = new ArrayList<>();
        Map<String, String> failedEmails = new HashMap<>();

        for (String email : request.getEmails()) {
            try {
                sendGridEmailService.sendEmail(
                        email,
                        request.getSubject(),
                        request.getBody()
                );
                successfulEmails.add(email);
                log.info("Email sent to: {}", email);
            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", email, e.getMessage());
                failedEmails.put(email, e.getMessage());
            }
        }

        return CustomMailResponseDTO.builder()
                .totalRecipients(request.getEmails().size())
                .successfulSends(successfulEmails.size())
                .failedSends(failedEmails.size())
                .successfulEmails(successfulEmails)
                .failedEmailsWithReason(failedEmails)
                .message("Email sending completed")
                .build();
    }
}
