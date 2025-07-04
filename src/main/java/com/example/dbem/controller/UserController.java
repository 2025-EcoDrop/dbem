package com.example.dbem.controller;

import com.example.dbem.service.UserService;
import com.example.dbem.dto.user.LoginRequestDTO;
import com.example.dbem.dto.user.SignupRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "User Sign-Up", description = "Registers a new user by accepting username, email, and password. " +
            "Validates input data and creates a new user account.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Parameter(description = "User registration data including username, email, and password.")
            @RequestBody SignupRequestDTO dto) {
        try {
            this.userService.validatePassword(dto.getPassword());
            this.userService.signup(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "User Login", description = "Authenticates a user with username and password. " +
            "Returns an access token if credentials are valid.")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "User credentials including username and password for authentication.")
            @RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        try {
            this.userService.login(dto, response);
        } catch (Exception e) {
            this.userService.logout(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.ok("로그인 성공");
    }

    @Operation(summary = "User Logout", description = "Logs out the authenticated user by invalidating the refresh token and clearing related session data.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        this.userService.logout(request, response);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @Operation(summary = "Refresh User Token", description = "Validates the refresh token and issues a new access token. The refresh token must be valid and not expired.")
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.userService.refresh(request, response);
            return ResponseEntity.ok("토큰 재발급 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
