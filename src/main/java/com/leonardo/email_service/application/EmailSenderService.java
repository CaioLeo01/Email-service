package com.leonardo.email_service.application;

import org.springframework.beans.factory.annotation.Autowired;

import com.leonardo.email_service.adapaters.EmailSenderGateway;
import com.leonardo.email_service.core.EmailSenderUserCase;





public class EmailSenderService implements EmailSenderUserCase {
    
    private final EmailSenderGateway emailSenderGateway;

    @Autowired
    public EmailSenderService(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;

    }


    @Override
    public void sendEmail(String to, String subject, String body) {
      this.emailSenderGateway.sendEmail(to, subject, body); 
    }
    
}
