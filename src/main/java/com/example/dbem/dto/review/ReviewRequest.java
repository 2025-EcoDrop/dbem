package com.example.dbem.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @Schema(description = "Medication product name entered by the user.", example = "타이레놀")
    private String productName;

    @Schema(description = "User's personal review or feedback on the medication.", example = "나랑 잘 맞음")
    private String review;

    @Schema(description = "Rating given by the user for the medication. Ranges from 0.0 to 5.0 in 0.5 increments.", example = "3.5")
    private BigDecimal rating;
}
