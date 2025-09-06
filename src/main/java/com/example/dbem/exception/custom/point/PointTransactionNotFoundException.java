package com.example.dbem.exception.custom.point;

public class PointTransactionNotFoundException extends RuntimeException {
    public PointTransactionNotFoundException(String message) {
        super(message);
    }
}
