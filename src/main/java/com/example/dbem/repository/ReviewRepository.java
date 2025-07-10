package com.example.dbem.repository;

import com.example.dbem.entity.Review;
import com.example.dbem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);
    Page<Review> findAllByAuthor(User user, Pageable pageable);
    Optional<Review> findByIdAndAuthor(Long id, User user);
}
