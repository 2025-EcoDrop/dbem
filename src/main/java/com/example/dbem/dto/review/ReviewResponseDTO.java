package com.example.dbem.dto.review;

import com.example.dbem.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    @Schema(description = "Unique identifier of the medication review.", example = "1")
    private Long id;

    @Schema(description = "Username of the review author.", example = "kiwi0123")
    private String authorName;

    @Schema(description = "Medication product name entered by the user.", example = "타이레놀")
    private String productName;

    @Schema(description = "User's personal review or feedback on the medication.", example = "나랑 잘 맞음")
    private String review;

    @Schema(description = "Rating given by the user for the medication. Ranges from 0.0 to 5.0 in 0.5 increments.", example = "3.5")
    private BigDecimal rating;

    @Schema(description = "Indicates whether the medication name exists in the public data source.", example = "false")
    private boolean publicData;

    @Schema(description = "Date and time when the review was created.", example = "2025-07-07 12:37:22.538649")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the review was last updated.", example = "2025-07-07 12:44:22.538649")
    private LocalDateTime updatedAt;

    public static ReviewResponseDTO toDto(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .authorName(review.getAuthor().getUsername())
                .productName(review.getProductName())
                .review(review.getReview())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
