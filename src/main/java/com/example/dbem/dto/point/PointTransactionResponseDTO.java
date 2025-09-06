package com.example.dbem.dto.point;

import com.example.dbem.entity.point.PointTransaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointTransactionResponseDTO {
    @Schema(description = "Unique identifier of the point transaction record.", example = "1")
    private Long id;

    @Schema(description = "Username of the user whose points were updated.", example = "kiwi0123")
    private String username;

    @Schema(description = "Amount of points changed in this transaction(positive for earning, negative for spending).", example = "100")
    private Integer amount;

    @Schema(description = "Type of point transaction, such as EARN, USE, CANCEL, INIT, EVENT or EXCHANGE.", example = "USE")
    private String type;

    @Schema(description = "Source or actor responsible for the point change.", example = "apple4567")
    private String source;

    @Schema(description = "Expiration date for the points, if applicable.", example = "2025-09-07 12:37:22.538649")
    private LocalDateTime expiredAt;

    public static PointTransactionResponseDTO toDto(PointTransaction pt) {
        return PointTransactionResponseDTO.builder()
                .id(pt.getId())
                .username(pt.getUser().getUsername())
                .amount(pt.getAmount())
                .type(pt.getType())
                .source(pt.getSource())
                .expiredAt(pt.getExpiredAt())
                .build();
    }
}
