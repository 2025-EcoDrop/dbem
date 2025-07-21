package com.example.dbem.exception.custom.user;

public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException() {
        super("비밀번호 형식이 유효하지 않습니다.");
    }

    public InvalidPasswordFormatException(String message) {
        super(message);
    }
}
