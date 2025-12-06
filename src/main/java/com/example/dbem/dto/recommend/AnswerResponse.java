package com.example.dbem.dto.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AnswerResponse {
    @Schema(description = "Top 5 recommended medications")
    private List<List<String>> answer;
}
