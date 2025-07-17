package com.example.dbem.exception.custom;

public class PublicDataNotFoundException extends RuntimeException {
    public PublicDataNotFoundException(String message) {
        super(message);
    }
}
