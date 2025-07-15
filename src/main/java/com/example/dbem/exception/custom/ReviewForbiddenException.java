package com.example.dbem.exception.custom;

public class ReviewForbiddenException extends RuntimeException {
    public ReviewForbiddenException(String message) {
        super(message);
    }
}
