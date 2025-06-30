package com.example.dbem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @Schema(description = "Username entered by the user during login.", example = "kiwi0123")
    private String username;

    @Schema(description = "Password entered by the user during login.")
    private String password;
}
