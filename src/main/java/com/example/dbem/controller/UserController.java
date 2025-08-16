package com.example.dbem.controller;

import com.example.dbem.dto.user.*;
import com.example.dbem.security.userdetails.UserDetailsImpl;
import com.example.dbem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "Check Username Availability", description = "Checks if the provided username is already taken before completing the sign-up process.")
    @PostMapping("/check-username")
    public ResponseEntity<?> checkUsername(@Valid @RequestBody UsernameCheckRequestDTO dto) {
        UsernameCheckResponseDTO response = this.userService.checkUsername(dto.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User Sign-Up", description = "Registers a new user by accepting username, email, and password. " +
            "Validates input data and creates a new user account.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Parameter(description = "User registration data including username, email, and password.")
            @Valid @RequestBody SignupRequestDTO dto) {
        this.userService.validatePassword(dto.getPassword());
        this.userService.signup(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "User Login", description = "Authenticates a user with username and password. " +
            "Returns an access token if credentials are valid.")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "User credentials including username and password for authentication.")
            @Valid @RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        this.userService.login(dto, response);
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
        this.userService.refresh(request, response);
        return ResponseEntity.ok("토큰 재발급 성공");
    }

    @Operation(summary = "Check Authentication Status", description = "Checks if the user is currently authenticated. Returns the username if the user is logged in.")
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = this.userService.checkAuth(userDetails.getUser());
        return ResponseEntity.ok(Map.of("username", username));
    }
}
