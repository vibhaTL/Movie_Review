package com.example.movierev.controller;

import com.example.movierev.dto.ReviewDto;
import com.example.movierev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // ✅ added import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // ✅ Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    // -------------------------
    // CREATE Review
    // -------------------------
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        logger.info("Received request to create review by '{}'", reviewDto.getReviewer());
        ReviewDto saved = reviewService.createReview(reviewDto);
        logger.info("Review created successfully with ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    // -------------------------
    // GET All Reviews
    // -------------------------
    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        logger.info("Received request to fetch all reviews");
        List<ReviewDto> reviews = reviewService.getAllReviews();
        logger.info("Returning {} reviews", reviews.size());
        return ResponseEntity.ok(reviews);
    }

    // -------------------------
    // GET Review by ID
    // -------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        logger.info("Received request to fetch review with ID: {}", id);
        ReviewDto review = reviewService.getReviewById(id);
        logger.info("Returning review for movie: '{}'", review.getMovieTitle());
        return ResponseEntity.ok(review);
    }

    // -------------------------
    // UPDATE Review
    // -------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDto reviewDto) {

        logger.info("Received request to update review with ID: {}", id);
        ReviewDto updated = reviewService.updateReview(id, reviewDto);
        logger.info("Review updated successfully with ID: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    // -------------------------
    // DELETE Review
    // -------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        logger.info("Received request to delete review with ID: {}", id);
        String msg = reviewService.deleteReview(id);
        logger.info("Review deleted successfully with ID: {}", id);
        return ResponseEntity.ok(msg);
    }
}
