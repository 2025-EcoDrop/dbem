package com.example.dbem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationRequestDTO {
    @Schema(description = "Email address used for email verification.", example = "abc123@google.com")
    private String email;
}
