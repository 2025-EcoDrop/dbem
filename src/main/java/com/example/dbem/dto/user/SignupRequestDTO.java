package com.example.dbem.dto.user;

import com.example.dbem.entity.User;
import com.example.dbem.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    @Schema(description = "Username chosen by the user during sign-up. This is used as the ID for logging in.", example = "kiwi0123")
    private String username;

    @Schema(description = "Email address entered by the user during sign-up.", example = "abc123@google.com")
    private String email;

    @Schema(description = "Password entered by the user during sign-up.")
    private String password;

    public static User toModel(SignupRequestDTO dto, UserRole role, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build();
    }
}
