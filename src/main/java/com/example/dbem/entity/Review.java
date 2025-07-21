package com.example.dbem.entity;

import com.example.dbem.dto.review.ReviewRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(length = 255)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String review;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    private boolean publicData;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void update(ReviewRequestDTO dto) {
        this.productName = dto.getProductName();
        this.review = dto.getReview();
        this.rating = dto.getRating();
        this.publicData = dto.isPublicData();
    }
}
