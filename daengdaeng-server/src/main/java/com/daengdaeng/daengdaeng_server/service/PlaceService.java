package com.daengdaeng.daengdaeng_server.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.daengdaeng.daengdaeng_server.dto.PlaceResponse;
import com.daengdaeng.daengdaeng_server.entity.Place;
import com.daengdaeng.daengdaeng_server.repository.PlaceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 전체 장소 조회
    public List<PlaceResponse> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    // 카테고리별 조회
    public List<PlaceResponse> getPlacesByCategory(String category) {
        return placeRepository.findByCategory(category)
                .stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    // 키워드 검색
    public List<PlaceResponse> searchPlaces(String keyword) {
        return placeRepository.findByNameContaining(keyword)
                .stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    // 단일 장소 조회
    public PlaceResponse getPlace(Long id) {
        return placeRepository.findById(id)
                .map(PlaceResponse::new)
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없어요!"));
    }

    public List<PlaceResponse> getPlaces(int size) {
        Pageable pageable = PageRequest.of(0, size);
        return placeRepository.findAll(pageable)
                .stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }


        // 지도 영역 기반 조회
    public List<PlaceResponse> getPlacesByBounds(Double south, Double north, Double west, Double east, String category, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Place> places;
        if (category != null && !category.isEmpty() && !category.equals("all")) {
                places = placeRepository.findByBoundsAndCategory(south, north, west, east, category, pageable);
        } else {
                places = placeRepository.findByBounds(south, north, west, east, pageable);
        }
        return places.stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
        }    
}