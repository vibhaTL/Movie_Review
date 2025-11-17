package com.example.movierev.service;

import com.example.movierev.dto.ReviewDto;
import java.util.List;

public interface ReviewService {

    ReviewDto createReview(ReviewDto reviewDto);

    List<ReviewDto> getAllReviews();

    ReviewDto getReviewById(Long id);

    ReviewDto updateReview(Long id, ReviewDto reviewDto);

    String deleteReview(Long id);
}
