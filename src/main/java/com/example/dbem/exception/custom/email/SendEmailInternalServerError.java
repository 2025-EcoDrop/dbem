package com.example.dbem.exception.custom.email;

public class SendEmailInternalServerError extends RuntimeException {
    public SendEmailInternalServerError(String message) {
        super(message);
    }
}
