package com.example.movierev.controller;

import com.example.movierev.dto.ReviewDto;
import com.example.movierev.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ReviewService reviewService;

    // ✅ Logger instance
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    // -------------------------
    // HOME PAGE - LIST REVIEWS
    // -------------------------
    @GetMapping("/")
    public String home(Model model) {
        logger.info("Fetching all reviews for home page");
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("reviewForm", new ReviewDto());
        return "index"; // Thymeleaf template
    }

    // -------------------------
    // ADD NEW REVIEW
    // -------------------------
    @PostMapping("/add")
    public String addReview(@ModelAttribute("reviewForm") ReviewDto reviewDto) {
        logger.info("Received request to add review by '{}'", reviewDto.getReviewer());
        reviewService.createReview(reviewDto);
        logger.info("Review added successfully for movie '{}'", reviewDto.getMovieTitle());
        return "redirect:/";
    }

    // -------------------------
    // EDIT REVIEW
    // -------------------------
    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable Long id, Model model) {
        logger.info("Received request to edit review with ID: {}", id);
        ReviewDto review = reviewService.getReviewById(id);
        model.addAttribute("reviewForm", review);
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "index";
    }

    // -------------------------
    // UPDATE REVIEW
    // -------------------------
    @PostMapping("/update/{id}")
    public String updateReview(@PathVariable Long id,
                               @ModelAttribute("reviewForm") ReviewDto reviewDto) {
        logger.info("Received request to update review with ID: {}", id);
        reviewService.updateReview(id, reviewDto);
        logger.info("Review updated successfully for movie '{}'", reviewDto.getMovieTitle());
        return "redirect:/";
    }

    // -------------------------
    // DELETE REVIEW
    // -------------------------
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        logger.info("Received request to delete review with ID: {}", id);
        reviewService.deleteReview(id);
        logger.info("Review deleted successfully with ID: {}", id);
        return "redirect:/";
    }
}
