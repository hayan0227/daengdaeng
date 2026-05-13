package com.daengdaeng.daengdaeng_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daengdaeng.daengdaeng_server.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // 장소별 리뷰 조회
    List<Review> findByPlaceIdOrderByCreatedAtDesc(Long placeId);
    
    // 유저별 리뷰 조회
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // 장소별 리뷰 개수
    Long countByPlaceId(Long placeId);
}