package com.example.dbem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 예약한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private User booker;

    // 수거 위치 (주소, 시(도)/구/동, 위도/경도)
    private String address;

    @Column(name="region_1depth")
    private String region1depth;
    @Column(name="region_2depth")
    private String region2depth;
    @Column(name="region_3depth")
    private String region3depth;

    private Double latitude;
    private Double longitude;

    private String status; // REQUESTED, IN_PROGRESS, COMPLETED

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 수거를 맡은 유저 (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collector_id")
    private User collector;

    public void update(String content, String address,
                       String region_1depth, String region_2depth, String region_3depth,
                       Double latitude, Double longitude) {
        this.content = content;
        this.address = address;
        this.region1depth = region_1depth;
        this.region2depth = region_2depth;
        this.region3depth = region_3depth;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void accept(String status, User collector) {
        this.status = status;
        this.collector = collector;
    }

    public void complete(String status) {
        this.status = status;
    }
}
