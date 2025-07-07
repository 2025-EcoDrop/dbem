package com.example.dbem.controller;

import com.example.dbem.dto.review.ReviewRequest;
import com.example.dbem.dto.review.ReviewResponse;
import com.example.dbem.security.userdetails.UserDetailsImpl;
import com.example.dbem.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/review")
@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "Check Reviews", description = "Retrieves a paginated list of notes written by the authenticated user. Supports sorting and pagination.")
    @GetMapping
    public ResponseEntity<?> getNotes(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "createdAt") String sortBy,
                                         @RequestParam(defaultValue = "desc") String sortDir) {
        Page<ReviewResponse> notePage =  this.reviewService.getReviews(userDetails.getUser(), page, size, sortBy, sortDir);

        return ResponseEntity.ok(notePage);
    }

    @Operation(summary = "Write a Review", description = "Creates a new note written by the authenticated user.")
    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody ReviewRequest dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReviewResponse reviewResponse = this.reviewService.createReview(dto, userDetails.getUser());
        return ResponseEntity.ok(reviewResponse);
    }

    @Operation(summary = "Check a Review", description = "Retrieves the details of a specific note written by the authenticated user.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ReviewResponse reviewResponse = this.reviewService.getReview(id, userDetails.getUser());
            return ResponseEntity.ok(reviewResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Update a Review", description = "Updates the content of a specific note written by the authenticated user.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@RequestBody ReviewRequest dto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ReviewResponse reviewResponse = this.reviewService.updateReview(dto, id, userDetails.getUser());
            return ResponseEntity.ok(reviewResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a Review", description = "Deletes a specific note written by the authenticated user.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            this.reviewService.deleteReview(id, userDetails.getUser());
            return ResponseEntity.ok("리뷰 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
