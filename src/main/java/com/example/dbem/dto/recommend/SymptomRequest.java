package com.example.dbem.dto.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SymptomRequest {
    @Schema(description = "Symptom for medication recommendation", example = "감기 걸린 거 같은데 약 추천해주라.")
    private String symptom;
}
