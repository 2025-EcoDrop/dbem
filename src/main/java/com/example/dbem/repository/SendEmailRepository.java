package com.example.dbem.repository;

import com.example.dbem.entity.SendEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SendEmailRepository extends JpaRepository<SendEmail, Long> {
    Optional<SendEmail> findByToken(String token);
    Optional<SendEmail> findByEmail(String email);
}
