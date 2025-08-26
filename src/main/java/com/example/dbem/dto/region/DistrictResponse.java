package com.example.dbem.dto.region;

import com.example.dbem.entity.region.City;
import com.example.dbem.entity.region.District;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictResponse {
    @Schema(description = "Unique identifier of the District.", example = "1")
    private Long id;

    @Schema(description = "The name of the district belonging to a specific city in South Korea.", example = "강동구")
    private String district;

    public static DistrictResponse toDto(District district) {
        return DistrictResponse.builder()
                .id(district.getId())
                .district(district.getDistrict())
                .build();
    }
}
