package com.example.movierev.service.impl;

import com.example.movierev.dto.ReviewDto;
import com.example.movierev.entity.ReviewEntity;
import com.example.movierev.repository.ReviewRepository;
import com.example.movierev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // ✅ import Logger
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    // ✅ Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        logger.info("Creating review for movie: '{}', by reviewer: '{}'", reviewDto.getMovieTitle(), reviewDto.getReviewer());
        ReviewEntity entity = mapToEntity(reviewDto);
        ReviewEntity saved = reviewRepository.save(entity);
        logger.info("Review saved successfully with ID: {}", saved.getId());
        return mapToDto(saved);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        logger.info("Fetching all reviews");
        List<ReviewDto> reviews = reviewRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.info("Total reviews fetched: {}", reviews.size());
        return reviews;
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        logger.info("Fetching review with ID: {}", id);
        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Review not found with ID: {}", id);
                    return new RuntimeException("Review not found");
                });
        logger.info("Review found for movie: '{}'", entity.getMovieTitle());
        return mapToDto(entity);
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        logger.info("Updating review with ID: {}", id);
        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Review not found for update with ID: {}", id);
                    return new RuntimeException("Review not found");
                });

        entity.setMovieTitle(reviewDto.getMovieTitle());
        entity.setReviewer(reviewDto.getReviewer());
        entity.setRating(reviewDto.getRating());
        entity.setComment(reviewDto.getComment());

        ReviewEntity updated = reviewRepository.save(entity);
        logger.info("Review updated successfully with ID: {}", updated.getId());
        return mapToDto(updated);
    }

    @Override
    public String deleteReview(Long id) {
        logger.info("Deleting review with ID: {}", id);
        ReviewEntity entity = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Review not found for deletion with ID: {}", id);
                    return new RuntimeException("Review not found");
                });

        reviewRepository.delete(entity);
        logger.info("Review deleted successfully with ID: {}", id);
        return "Review deleted successfully!";
    }

    // ------------------------
    // Mapping Methods
    // ------------------------
    private ReviewDto mapToDto(ReviewEntity entity) {
        return new ReviewDto(
                entity.getId(),
                entity.getMovieTitle(),
                entity.getReviewer(),
                entity.getRating(),
                entity.getComment()
        );
    }

    private ReviewEntity mapToEntity(ReviewDto dto) {
        ReviewEntity entity = new ReviewEntity();
        entity.setMovieTitle(dto.getMovieTitle());
        entity.setReviewer(dto.getReviewer());
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
        return entity;
    }
}
