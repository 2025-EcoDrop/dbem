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
public class UsernameCheckRequestDTO {
    @Schema(description = "Username to check for existence.", example = "apple4567")
    private String username;
}
