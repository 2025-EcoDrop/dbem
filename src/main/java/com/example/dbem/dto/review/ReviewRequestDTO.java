package com.example.dbem.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {
    @Schema(description = "Medication product name entered by the user.", example = "타이레놀")
    @NotEmpty(message = "제품명을 작성해 주세요.")
    private String productName;

    @Schema(description = "User's personal review or feedback on the medication.", example = "복통에 효과적이다.")
    @NotEmpty(message = "리뷰를 작성해 주세요.")
    private String review;

    @Schema(description = "Rating given by the user for the medication. Ranges from 0.0 to 5.0 in 0.5 increments.", example = "3.5")
    @DecimalMin(value = "0.0", message = "별점은 최소 0점부터 가능합니다.")
    @DecimalMax(value = "5.0", message = "별점은 최대 5점까지 가능합니다.")
    private BigDecimal rating;

    @Schema(description = "Indicates whether the medication name exists in the public data source.", example = "false")
    private boolean publicData;
}
