package com.example.dbem.dto.user;

import com.example.dbem.entity.User;
import com.example.dbem.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    @Schema(description = "Username chosen by the user during sign-up. This is used as the ID for logging in.", example = "kiwi0123")
    @Size(min = 6, max = 25, message = "아이디는 최소 6글자, 최대 25글자여야 합니다.")
    @NotEmpty(message = "아이디는 필수 항목 입니다.")
    private String username;

    @Schema(description = "Email address entered by the user during sign-up.", example = "abc123@google.com")
    @NotEmpty(message = "이메일은 필수 항목 입니다.")
    private String email;

    @Schema(description = "Password entered by the user during sign-up.")
    @NotEmpty(message = "비밀번호는 필수 항목 입니다.")
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
