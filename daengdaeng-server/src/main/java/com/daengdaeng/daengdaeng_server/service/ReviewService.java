package com.daengdaeng.daengdaeng_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.daengdaeng.daengdaeng_server.dto.ReviewRequest;
import com.daengdaeng.daengdaeng_server.dto.ReviewResponse;
import com.daengdaeng.daengdaeng_server.entity.Place;
import com.daengdaeng.daengdaeng_server.entity.Review;
import com.daengdaeng.daengdaeng_server.entity.User;
import com.daengdaeng.daengdaeng_server.repository.PlaceRepository;
import com.daengdaeng.daengdaeng_server.repository.ReviewRepository;
import com.daengdaeng.daengdaeng_server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    // 리뷰 작성
    public ReviewResponse createReview(ReviewRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요!"));
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없어요!"));

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setStars(request.getStars());
        review.setContent(request.getContent());
        reviewRepository.save(review);

        updatePlaceRating(place);
        return new ReviewResponse(review);
    }

    // 장소별 리뷰 조회
    public List<ReviewResponse> getReviewsByPlace(Long placeId) {
        return reviewRepository.findByPlaceIdOrderByCreatedAtDesc(placeId)
                .stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    // 내가 쓴 리뷰 조회
    public List<ReviewResponse> getMyReviews(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요!"));
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    // 리뷰 수정
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request, String email) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없어요!"));
        if (!review.getUser().getEmail().equals(email)) {
            throw new RuntimeException("본인 리뷰만 수정할 수 있어요!");
        }
        review.setStars(request.getStars());
        review.setContent(request.getContent());
        reviewRepository.save(review);

        updatePlaceRating(review.getPlace());
        return new ReviewResponse(review);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId, String email) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없어요!"));
        if (!review.getUser().getEmail().equals(email)) {
            throw new RuntimeException("본인 리뷰만 삭제할 수 있어요!");
        }
        Place place = review.getPlace();
        reviewRepository.delete(review);
        updatePlaceRating(place);
    }

    // 평균 별점 업데이트
    private void updatePlaceRating(Place place) {
        List<Review> reviews = reviewRepository.findByPlaceIdOrderByCreatedAtDesc(place.getId());
        double avgRating = reviews.stream()
                .mapToInt(Review::getStars)
                .average()
                .orElse(0.0);
        place.setRating(Math.round(avgRating * 10.0) / 10.0);
        placeRepository.save(place);
    }
}