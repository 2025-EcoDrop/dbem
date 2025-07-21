package com.example.dbem.service;

import com.example.dbem.dto.review.ReviewRequestDTO;
import com.example.dbem.dto.review.ReviewResponseDTO;
import com.example.dbem.entity.Review;
import com.example.dbem.entity.User;
import com.example.dbem.exception.custom.review.ReviewForbiddenException;
import com.example.dbem.exception.custom.review.ReviewNotFoundException;
import com.example.dbem.repository.ReviewRepository;
import com.example.dbem.spec.ReviewSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Page<ReviewResponseDTO> getReviews(User user, int page, int size, String sortBy, String sortDir, String kw) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (kw.isEmpty()) {
            return this.reviewRepository.findAllByAuthor(user, pageable).map(ReviewResponseDTO::toDto);
        } else {
            Specification<Review> spec = ReviewSpecification.searchAll(kw, user);
            return this.reviewRepository.findAll(spec, pageable).map(ReviewResponseDTO::toDto);
        }
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO dto, User user) {
        Review review = this.reviewRepository.save(
                Review.builder()
                        .author(user)
                        .productName(dto.getProductName())
                        .review(dto.getReview())
                        .rating(dto.getRating())
                        .publicData(dto.isPublicData())
                        .build());

        return ReviewResponseDTO.toDto(review);
    }

    @Transactional
    public ReviewResponseDTO getReview(Long id, User user) {
        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        if (!review.getAuthor().getUsername().equals(user.getUsername())) {
            throw new ReviewForbiddenException("해당 리뷰에 접근 권한이 없습니다.");
        }

        return ReviewResponseDTO.toDto(review);
    }

    @Transactional
    public ReviewResponseDTO updateReview(ReviewRequestDTO dto, Long id, User user) {
        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        if (!review.getAuthor().getUsername().equals(user.getUsername())) {
            throw new ReviewForbiddenException("해당 리뷰에 접근 권한이 없습니다.");
        }

        review.update(dto);
        review = this.reviewRepository.saveAndFlush(review);

        return ReviewResponseDTO.toDto(review);
    }

    @Transactional
    public void deleteReview(Long id, User user) {
        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        if (!review.getAuthor().getUsername().equals(user.getUsername())) {
            throw new ReviewForbiddenException("해당 리뷰에 접근 권한이 없습니다.");
        }

        this.reviewRepository.delete(review);
    }
}
