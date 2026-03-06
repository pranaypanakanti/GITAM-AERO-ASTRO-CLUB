package com.GAAC.GAAC.Backend.controller;

import com.GAAC.GAAC.Backend.model.MailContent;
import com.GAAC.GAAC.Backend.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/new-mail-content")
    public ResponseEntity<?> createMailContent(@Valid @RequestBody MailContent newMailContent){
        try{
            emailService.saveMailContent(newMailContent);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
