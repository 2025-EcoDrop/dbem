package com.example.dbem.service;

import com.example.dbem.dto.review.ReviewRequest;
import com.example.dbem.dto.review.ReviewResponse;
import com.example.dbem.entity.Review;
import com.example.dbem.entity.User;
import com.example.dbem.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Page<ReviewResponse> getReviews(User user, int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return this.reviewRepository.findAllByAuthor(user, pageable).map(ReviewResponse::toDto);
    }

    public ReviewResponse createReview(ReviewRequest dto, User user) {
        Review review = this.reviewRepository.save(
                Review.builder()
                        .author(user)
                        .productName(dto.getProductName())
                        .review(dto.getReview())
                        .rating(dto.getRating())
                        .build());

        return ReviewResponse.toDto(review);
    }

    @Transactional
    public ReviewResponse getReview(Long id, User user) {
        Review review = this.reviewRepository.findByIdAndAuthor(id, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰에 접근 권한이 없습니다."));

        return ReviewResponse.toDto(review);
    }

    @Transactional
    public ReviewResponse updateReview(ReviewRequest dto, Long id, User user) {
        Review review = this.reviewRepository.findByIdAndAuthor(id, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
        review.setProductName(dto.getProductName());
        review.setReview(dto.getReview());
        review.setRating(dto.getRating());
        Review newReview = this.reviewRepository.saveAndFlush(review);

        return ReviewResponse.toDto(newReview);
    }

    @Transactional
    public void deleteReview(Long id, User user) {
        Review review = this.reviewRepository.findByIdAndAuthor(id, user)
                .orElseThrow(() -> new AccessDeniedException("해당 리뷰에 접근 권한이 없습니다."));
        this.reviewRepository.delete(review);
    }
}
