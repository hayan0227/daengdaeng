package com.daengdaeng.daengdaeng_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daengdaeng.daengdaeng_server.dto.FavoriteResponse;
import com.daengdaeng.daengdaeng_server.service.FavoriteService;
import com.daengdaeng.daengdaeng_server.util.JwtUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;

    // 즐겨찾기 추가/취소 토글
    @PostMapping("/{placeId}")
    public ResponseEntity<Map<String, Boolean>> toggleFavorite(
            @PathVariable Long placeId,
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        boolean isFavorite = favoriteService.toggleFavorite(placeId, email);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }

    // 내 즐겨찾기 목록
    @GetMapping("/my")
    public ResponseEntity<List<FavoriteResponse>> getMyFavorites(
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(favoriteService.getMyFavorites(email));
    }

    // 즐겨찾기 여부 확인
    @GetMapping("/{placeId}/check")
    public ResponseEntity<Map<String, Boolean>> isFavorite(
            @PathVariable Long placeId,
            @RequestHeader("Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        boolean isFavorite = favoriteService.isFavorite(placeId, email);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }
}