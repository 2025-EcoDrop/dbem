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
@Table(name = "bookings")
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
    private String region_1depth;
    private String region_2depth;
    private String region_3depth;
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

    public void accept(String status, User collector) {
        this.status = status;
        this.collector = collector;
    }

    public void complete(String status) {
        this.status = status;
    }
}
