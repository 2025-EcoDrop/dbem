package com.example.dbem.repository;

import com.example.dbem.entity.SendEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SendEmailRepository extends JpaRepository<SendEmail, Long> {
    Optional<SendEmail> findByToken(String token);
    Optional<SendEmail> findByEmail(String email);

    Page<SendEmail> findByVerificationAndCreatedAtBefore(boolean verification, LocalDateTime threshold, Pageable pageable);
}
