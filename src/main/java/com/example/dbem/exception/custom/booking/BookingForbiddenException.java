package com.example.dbem.exception.custom.booking;

public class BookingForbiddenException extends RuntimeException {
    public BookingForbiddenException(String message) {
        super(message);
    }
}
