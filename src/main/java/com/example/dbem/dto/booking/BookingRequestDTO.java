package com.example.dbem.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    @Schema(description = "User's reservation request.", example = "저 대신 주변 약국에 약을 버려줄 사람 구해요.")
    @NotEmpty(message = "요청할 내용을 작성해 주세요.")
    private String content;

    @Schema(description = "Address of the user who made the reservation.", example = "서울특별시 강남구 테헤란로 123")
    private String address;

    @Schema(description = "The first depth of the area of the user who made the reservation.", example = "서울특별시")
    private String region_1depth;

    @Schema(description = "The second depth of the user's area that has made the reservation.", example = "강남구")
    private String region_2depth;

    @Schema(description = "The third depth of the area of the user who made the reservation.", example = "역삼동")
    private String region_3depth;

    @Schema(description = "Latitude of the user who made the reservation.", example = "127.027619")
    private Double latitude;

    @Schema(description = "Longitude of the user who made the reservation.", example = "37.497942")
    private Double longitude;
}
