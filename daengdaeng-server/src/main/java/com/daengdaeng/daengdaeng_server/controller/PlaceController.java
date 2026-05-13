package com.daengdaeng.daengdaeng_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daengdaeng.daengdaeng_server.dto.PlaceResponse;
import com.daengdaeng.daengdaeng_server.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    // 전체 or 영역 기반 조회
    @GetMapping
    public ResponseEntity<List<PlaceResponse>> getPlaces(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double south,
            @RequestParam(required = false) Double north,
            @RequestParam(required = false) Double west,
            @RequestParam(required = false) Double east,
            @RequestParam(defaultValue = "200") int size) {

        List<PlaceResponse> places;

        if (keyword != null && !keyword.isEmpty()) {
            places = placeService.searchPlaces(keyword);
        } else if (south != null && north != null && west != null && east != null) {
            places = placeService.getPlacesByBounds(south, north, west, east, category, size);
        } else {
            places = placeService.getPlaces(size);
        }

        return ResponseEntity.ok(places);
    }

    // 단일 장소 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponse> getPlace(@PathVariable Long id) {
        return ResponseEntity.ok(placeService.getPlace(id));
    }
}