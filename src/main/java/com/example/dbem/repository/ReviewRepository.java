package com.example.dbem.repository;

import com.example.dbem.entity.Review;
import com.example.dbem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);
    Page<Review> findAllByAuthor(User user, Pageable pageable);
}
