package com.daengdaeng.daengdaeng_server.repository;

import com.daengdaeng.daengdaeng_server.entity.Place;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    
    // 카테고리로 검색
    List<Place> findByCategory(String category);
    
    // 이름으로 검색
    List<Place> findByNameContaining(String keyword);
    
    // 카테고리 + 이름으로 검색
    List<Place> findByCategoryAndNameContaining(String category, String keyword);

    // 지도 영역 기반 조회
    @Query("SELECT p FROM Place p WHERE p.lat BETWEEN :south AND :north AND p.lng BETWEEN :west AND :east")
    List<Place> findByBounds(
        @Param("south") Double south,
        @Param("north") Double north,
        @Param("west") Double west,
        @Param("east") Double east,
        Pageable pageable
    );

    // 지도 영역 + 카테고리
    @Query("SELECT p FROM Place p WHERE p.lat BETWEEN :south AND :north AND p.lng BETWEEN :west AND :east AND p.category = :category")
    List<Place> findByBoundsAndCategory(
        @Param("south") Double south,
        @Param("north") Double north,
        @Param("west") Double west,
        @Param("east") Double east,
        @Param("category") String category,
        Pageable pageable
    );
}