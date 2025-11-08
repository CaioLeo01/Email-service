package com.leonardo.email_service.exceptions;

public record EmailRequest(String to, String subject, String body) {

}
