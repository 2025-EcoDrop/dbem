package com.example.dbem.dto.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryRequest {
    @Schema(description = "Symptom for medication recommendation", example = "감기 걸린 거 같은데 약 추천해주라.")
    private String query;

    @Schema(description = "User ID for review-based recommendation", example = "1")
    private Long user_id;
}
