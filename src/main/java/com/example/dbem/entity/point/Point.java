package com.example.dbem.entity.point;

import com.example.dbem.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer balance;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updatePlus(Integer points) {
        this.balance += points;
    }

    public void updateMinus(Integer points) {
        this.balance -= points;
    }
}
