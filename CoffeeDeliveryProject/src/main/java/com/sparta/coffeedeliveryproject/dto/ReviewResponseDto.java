package com.sparta.coffeedeliveryproject.dto;

import com.sparta.coffeedeliveryproject.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    private Long reviewId;
    private String reviewContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int reviewLikeCount;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewContent = review.getReviewContent();
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
        this.reviewLikeCount = review.getReviewLikeCount();
    }

}