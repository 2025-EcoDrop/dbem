package com.example.dbem.exception;

import com.example.dbem.exception.custom.*;
import com.example.dbem.exception.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponseDTO.builder()
                        .status(404)
                        .code("Review_Not_Found")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ReviewForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleReviewForbiddenException(ReviewForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponseDTO.builder()
                        .status(403)
                        .code("Review_Forbidden")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingNotFoundException(BookingNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponseDTO.builder()
                        .status(404)
                        .code("Booking_Not_Found")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BookingForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingForbiddenException(BookingForbiddenException e) {
        return ResponseEntity.status(403).body(
                ErrorResponseDTO.builder()
                        .status(403)
                        .code("Booking_Forbidden")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(PublicDataNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePublicDataNotFoundException(PublicDataNotFoundException e) {
        return ResponseEntity.status(404).body(
                ErrorResponseDTO.builder()
                        .status(404)
                        .code("Public_Data_Not_Found")
                        .message(e.getMessage())
                        .build()
        );
    }
}