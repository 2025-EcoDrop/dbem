package com.example.dbem.dto.region;

import com.example.dbem.entity.region.City;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {
    @Schema(description = "Unique identifier of the City.", example = "1")
    private Long id;

    @Schema(description = "The name of the city in South Korea used for region selection.", example = "서울특별시")
    private String city;

    public static CityResponse toDto(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .city(city.getCity())
                .build();
    }
}
