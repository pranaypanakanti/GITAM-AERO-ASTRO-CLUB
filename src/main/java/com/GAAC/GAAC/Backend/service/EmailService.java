package com.GAAC.GAAC.Backend.service;

import com.GAAC.GAAC.Backend.model.MailContent;
import com.GAAC.GAAC.Backend.repository.MailContentRepo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailContentRepo mailContentRepo;

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

}
