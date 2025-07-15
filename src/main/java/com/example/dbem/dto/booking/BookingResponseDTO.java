package com.example.dbem.dto.booking;

import com.example.dbem.entity.Booking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    @Schema(description = "Unique identifier of the reservation.", example = "1")
    private Long id;

    @Schema(description = "User's reservation request.", example = "저 대신 주변 약국에 약을 버려줄 사람 구해요.")
    private String content;

    @Schema(description = "Username of user who made reservation", example = "kiwi0123")
    private String bookerName;

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

    @Schema(description = "Current reservation status.", example = "REQUESTED or IN_PROGRESS or COMPLETED")
    private String status;

    @Schema(description = "Username of the user who accepted the reservation.", example = "melon1212")
    private String collectorName;

    @Schema(description = "Date and time when the reservation was created.", example = "2025-07-07 12:37:22.538649")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the reservation was last updated.", example = "2025-07-07 12:44:22.538649")
    private LocalDateTime updatedAt;

    public static BookingResponseDTO toDto(Booking booking) {
        if (booking.getCollector() == null) {
            return BookingResponseDTO.builder()
                    .id(booking.getId())
                    .content(booking.getContent())
                    .bookerName(booking.getBooker().getUsername())
                    .address(booking.getAddress())
                    .region_1depth(booking.getRegion_1depth())
                    .region_2depth(booking.getRegion_2depth())
                    .region_3depth(booking.getRegion_3depth())
                    .latitude(booking.getLatitude())
                    .longitude(booking.getLongitude())
                    .status(booking.getStatus())
                    .createdAt(booking.getCreatedAt())
                    .build();
        } else {
            return BookingResponseDTO.builder()
                    .id(booking.getId())
                    .content(booking.getContent())
                    .bookerName(booking.getBooker().getUsername())
                    .address(booking.getAddress())
                    .region_1depth(booking.getRegion_1depth())
                    .region_2depth(booking.getRegion_2depth())
                    .region_3depth(booking.getRegion_3depth())
                    .latitude(booking.getLatitude())
                    .longitude(booking.getLongitude())
                    .status(booking.getStatus())
                    .createdAt(booking.getCreatedAt())
                    .collectorName(booking.getCollector().getUsername())
                    .updatedAt(booking.getUpdatedAt())
                    .build();
        }
    }
}
