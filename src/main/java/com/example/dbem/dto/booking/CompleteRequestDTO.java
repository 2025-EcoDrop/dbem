package com.example.dbem.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteRequestDTO {
    @Schema(description = "The latitude coordinate of the booking location.", example = "127.027619")
    private Double latitude1;
    @Schema(description = "The longitude coordinate of the booking location.", example = "37.497942")
    private Double longitude1;

    @Schema(description = "The latitude coordinate of the collector’s current location", example = "127.027619")
    private Double latitude2;
    @Schema(description = "The longitude coordinate of the collector’s current location.", example = "37.497942")
    private Double longitude2;
}
