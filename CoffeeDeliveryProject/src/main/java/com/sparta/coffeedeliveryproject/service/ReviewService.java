package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.dto.ReviewRequestDto;
import com.sparta.coffeedeliveryproject.dto.ReviewResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Order;
import com.sparta.coffeedeliveryproject.entity.Review;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import com.sparta.coffeedeliveryproject.repository.OrderRepository;
import com.sparta.coffeedeliveryproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;
    private final OrderRepository orderRepository;

    public ReviewResponseDto createReview(Long cafeId, Long orderId, ReviewRequestDto requestDto, User user) {

        Cafe cafe = findCafeById(cafeId);
        Order order = findOrderById(orderId);
        Review review = new Review(requestDto, cafe, order, user);
        Review saveReview = reviewRepository.save(review);
        return new ReviewResponseDto(review);

    }

    public List<ReviewResponseDto> getReviewCafe(Long cafeId) {

        List<Review> reviews = reviewRepository.findAllByCafeCafeId(cafeId);

        return reviews.stream().map(ReviewResponseDto::new).toList();

    }

    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {

        Review review = findReviewById(reviewId);

        if (review.getUser().getUserId() == user.getUserId()) {
            review.update(requestDto);
            return new ReviewResponseDto(review);
        } else throw new IllegalArgumentException("본인이 작성한 리뷰만 수정 가능합니다.");

    }

    public ResponseEntity<String> deleteReview(Long reviewId, User user) {

        Review review = findReviewById(reviewId);

        if (review.getUser().getUserId() == user.getUserId()) {
            reviewRepository.delete(review);
            return ResponseEntity.ok("리뷰가 삭제되었습니다.");
        } else throw new IllegalArgumentException("본인이 작성한 리뷰만 삭제 가능합니다.");

    }

    private Review findReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

    }

    private Order findOrderById(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

    }

    private Cafe findCafeById(Long cafeId) {

        return cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException("해당 카페를 찾을 수 없습니다."));

    }

    public Page<ReviewResponseDto> getUserFavoriteReviews(int page, User user) {

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Direction.DESC, "createdAt"));
        Page<ReviewResponseDto> reviewPage = reviewRepository.findFavoriteReviewsByUserId(user.getUserId(), pageable).map(ReviewResponseDto::new);

        if (reviewPage.isEmpty()) {
            throw new IllegalArgumentException("작성된 리뷰가 없거나, " + (page + 1) + " 페이지에 리뷰가 없습니다.");
        }

        return reviewPage;
    }

}
