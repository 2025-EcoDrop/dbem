package com.example.dbem.exception.custom.review;

public class ReviewForbiddenException extends RuntimeException {
    public ReviewForbiddenException(String message) {
        super(message);
    }
}
