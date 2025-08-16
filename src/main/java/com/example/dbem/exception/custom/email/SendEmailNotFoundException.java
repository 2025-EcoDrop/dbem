package com.example.dbem.exception.custom.email;

public class SendEmailNotFoundException extends RuntimeException {
    public SendEmailNotFoundException(String message) {
        super(message);
    }
}
