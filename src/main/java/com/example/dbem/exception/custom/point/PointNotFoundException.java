package com.example.dbem.exception.custom.point;

public class PointNotFoundException extends RuntimeException {
    public PointNotFoundException(String message) {
        super(message);
    }
}
