package com.example.dbem.dto.point;

import com.example.dbem.entity.User;
import com.example.dbem.entity.point.Point;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPointsInfoResponseDTO {
    @Schema(description = "Unique identifier of the user.", example = "1")
    private Long userId;

    @Schema(description = "Username of the user.", example = "kiwi0123")
    private String username;

    @Schema(description = "Email address of the user.", example = "abc123@google.com")
    private String email;

    @Schema(description = "Name provided by the social login provider.", example = "홍길동")
    private String name;

    @Schema(description = "Social login provider used for authentication.", example = "kakao")
    private String provider;

    @Schema(description = "Unique identifier of the point record.", example = "1")
    private Long pointId;

    @Schema(description = "Current available point balance of the user.", example = "1000")
    private Integer balance;

    public static MyPointsInfoResponseDTO toDto(User user, Point point) {
        return MyPointsInfoResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .provider(user.getProvider())
                .pointId(point.getId())
                .balance(point.getBalance())
                .build();
    }
}
