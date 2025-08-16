package com.example.dbem.controller;

import com.example.dbem.dto.user.EmailVerificationRequestDTO;
import com.example.dbem.service.SendEmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/send")
@RequiredArgsConstructor
@RestController
public class SendEmailController {
    private final SendEmailService sendEmailService;

    @Operation(summary = "Send Email Verification", description = "Sends a verification email to the provided email address during the sign-up process.")
    @PostMapping("/send-verification")
    public ResponseEntity<?> sendEmailVerification(@Valid @RequestBody EmailVerificationRequestDTO dto) {
        this.sendEmailService.sendEmailVerification(dto.getEmail());
        return ResponseEntity.ok("이메일 인증 메일 전송 완료");
    }

    @Operation(summary = "Check Email Verification Status", description = "Checks whether the provided email address has been successfully verified.")
    @PostMapping("/get-verification")
    public ResponseEntity<?> getEmailVerification(@Valid @RequestBody EmailVerificationRequestDTO dto) {
        boolean check = this.sendEmailService.getEmailVerification(dto.getEmail());

        if (!check) {
            return ResponseEntity.ok("이메일 인증 미완료");
        }

        return ResponseEntity.ok("이메일 인증 완료");
    }

    @Operation(summary = "Verify Email", description = "Verifies the user's email address using the verification token received via email.")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmailVerification(@RequestParam String token) {
        this.sendEmailService.verifyEmailVerification(token);
        return ResponseEntity.ok("이메일 인증 메일 확인 완료");
    }
}
