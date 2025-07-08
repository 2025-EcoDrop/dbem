package com.example.dbem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @Schema(description = "Username entered by the user during login.", example = "kiwi0123")
    @NotEmpty(message = "아이디를 작성해 주세요.")
    private String username;

    @Schema(description = "Password entered by the user during login.")
    @NotEmpty(message = "비밀번호를 작성해 주세요.")
    private String password;
}
