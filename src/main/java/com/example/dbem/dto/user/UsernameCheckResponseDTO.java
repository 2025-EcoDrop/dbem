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
public class UsernameCheckResponseDTO {
    @Schema(description = "Indicates whether the username already exists.", example = "false")
    private boolean exists;

    @Schema(description = "Additional message about the username existence check.")
    private String message;
}
