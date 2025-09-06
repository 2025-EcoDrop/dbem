package com.example.dbem.entity.point;

import com.example.dbem.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point_transaction")
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String source;

    private LocalDateTime expiredAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public void updateSource(String source) {
        this.source = source;
    }
}
