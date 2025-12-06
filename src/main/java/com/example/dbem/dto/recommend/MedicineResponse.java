package com.example.dbem.dto.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MedicineResponse {
    @Schema(description = "Pharmaceutical company name")
    private String entpName;

    @Schema(description = "Medication name")
    private String itemName;
}
