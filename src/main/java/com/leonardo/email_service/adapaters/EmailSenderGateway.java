package com.leonardo.email_service.adapaters;

public interface EmailSenderGateway {

 void sendEmail(String to, String subject, String body);
}
