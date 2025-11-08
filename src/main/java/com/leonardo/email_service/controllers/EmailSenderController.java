package com.leonardo.email_service.controllers;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.email_service.application.EmailSenderService;
import com.leonardo.email_service.exceptions.EmailRequest;
import com.leonardo.email_service.exceptions.EmailServiceException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/email")
public class EmailSenderController {
    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }
    
    @PostMapping()
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        
    try {
        this.emailSenderService.sendEmail(request.to(), request.subject(), request.body());
        return ResponseEntity.ok("Email sent successfully");
    }catch (EmailServiceException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: ");
    }
    }
    


}
