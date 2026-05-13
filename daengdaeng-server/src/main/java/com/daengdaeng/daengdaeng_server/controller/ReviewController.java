package com.daengdaeng.daengdaeng_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daengdaeng.daengdaeng_server.dto.ReviewRequest;
import com.daengdaeng.daengdaeng_server.dto.ReviewResponse;
import com.daengdaeng.daengdaeng_server.service.ReviewService;
import com.daengdaeng.daengdaeng_server.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    // 리뷰 작성
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @RequestBody ReviewRequest request,
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(reviewService.createReview(request, email));
    }

    // 장소별 리뷰 조회
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByPlace(@PathVariable Long placeId) {
        return ResponseEntity.ok(reviewService.getReviewsByPlace(placeId));
    }

    // 내가 쓴 리뷰 조회
    @GetMapping("/my")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(reviewService.getMyReviews(email));
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest request,
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(reviewService.updateReview(reviewId, request, email));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        reviewService.deleteReview(reviewId, email);
        return ResponseEntity.ok().build();
    }
}