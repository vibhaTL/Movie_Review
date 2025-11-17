package com.example.movierev.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    @NotBlank(message = "Movie title cannot be empty")
    private String movieTitle;

    @NotBlank(message = "Reviewer name cannot be empty")
    private String reviewer;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private int rating;

    private String comment;
}
