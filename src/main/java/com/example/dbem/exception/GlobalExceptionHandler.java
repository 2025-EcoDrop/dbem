package com.example.dbem.exception;

import com.example.dbem.exception.custom.*;
import com.example.dbem.exception.custom.booking.BookingForbiddenException;
import com.example.dbem.exception.custom.booking.BookingNotFoundException;
import com.example.dbem.exception.custom.email.SendEmailInternalServerError;
import com.example.dbem.exception.custom.email.SendEmailNotFoundException;
import com.example.dbem.exception.custom.review.ReviewForbiddenException;
import com.example.dbem.exception.custom.review.ReviewNotFoundException;
import com.example.dbem.exception.custom.user.InvalidPasswordFormatException;
import com.example.dbem.exception.custom.user.InvalidRefreshTokenException;
import com.example.dbem.exception.custom.user.UnauthorizedAccessException;
import com.example.dbem.exception.custom.user.UserExistsException;
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

    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidPasswordFormatException(InvalidPasswordFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponseDTO.builder()
                        .status(400)
                        .code("Invalid_Password_Format")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserExistsException(UserExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponseDTO.builder()
                        .status(409)
                        .code("User_Exists")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponseDTO.builder()
                        .status(401)
                        .code("Invalid_Refresh_Token")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponseDTO.builder()
                        .status(401)
                        .code("Unauthorized_Access")
                        .message(e.getMessage())
                        .build()
        );
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
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponseDTO.builder()
                        .status(403)
                        .code("Booking_Forbidden")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(PublicDataNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePublicDataNotFoundException(PublicDataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponseDTO.builder()
                        .status(404)
                        .code("Public_Data_Not_Found")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(SendEmailNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleSendEmailNotFoundException(PublicDataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponseDTO.builder()
                        .status(404)
                        .code("Send_Email_Not_Found")
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(SendEmailInternalServerError.class)
    public ResponseEntity<ErrorResponseDTO> handleSendEmailInternalServerError(PublicDataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponseDTO.builder()
                        .status(500)
                        .code("Send_Email_INTERNAL_SERVER_ERROR")
                        .message(e.getMessage())
                        .build()
        );
    }
}