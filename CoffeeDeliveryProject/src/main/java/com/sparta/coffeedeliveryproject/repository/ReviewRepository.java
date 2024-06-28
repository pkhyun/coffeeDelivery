package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByCafeCafeId(Long cafeId);

    @Query("SELECT r "
        + "FROM Review r "
        + "JOIN ReviewLike rl ON r.reviewId = rl.review.reviewId "
        + "WHERE rl.user.userId = :userId")
    Page<Review> findFavoriteReviewsByUserId(Long userId, Pageable pageable);
}
