package com.example.dbem.dto.region;

import com.example.dbem.entity.region.Town;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TownResponse {
    @Schema(description = "Unique identifier of the Town.", example = "1")
    private Long id;

    @Schema(description = "The legal name of the town (eup/myeon/dong) in South Korea.", example = "명일1동")
    private String town;

    public static TownResponse toDto(Town town) {
        return TownResponse.builder()
                .id(town.getId())
                .town(town.getTown())
                .build();
    }
}
