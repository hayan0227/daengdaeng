package com.daengdaeng.daengdaeng_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daengdaeng.daengdaeng_server.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // 유저별 즐겨찾기 조회
    List<Favorite> findByUserId(Long userId);

    // 즐겨찾기 여부 확인
    Optional<Favorite> findByUserIdAndPlaceId(Long userId, Long placeId);

    // 즐겨찾기 여부 boolean
    boolean existsByUserIdAndPlaceId(Long userId, Long placeId);
}