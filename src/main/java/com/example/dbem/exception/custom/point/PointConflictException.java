package com.example.dbem.exception.custom.point;

public class PointConflictException extends RuntimeException {
    public PointConflictException(String message) {
        super(message);
    }
}
