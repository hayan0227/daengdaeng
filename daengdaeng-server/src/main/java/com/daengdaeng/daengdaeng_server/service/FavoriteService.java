package com.daengdaeng.daengdaeng_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.daengdaeng.daengdaeng_server.dto.FavoriteResponse;
import com.daengdaeng.daengdaeng_server.entity.Favorite;
import com.daengdaeng.daengdaeng_server.entity.Place;
import com.daengdaeng.daengdaeng_server.entity.User;
import com.daengdaeng.daengdaeng_server.repository.FavoriteRepository;
import com.daengdaeng.daengdaeng_server.repository.PlaceRepository;
import com.daengdaeng.daengdaeng_server.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    // 즐겨찾기 추가/취소 (토글)
    public boolean toggleFavorite(Long placeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요!"));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없어요!"));

        Optional<Favorite> existing = favoriteRepository.findByUserIdAndPlaceId(user.getId(), placeId);

        if (existing.isPresent()) {
            // 이미 즐겨찾기 되어있으면 취소
            favoriteRepository.delete(existing.get());
            return false;
        } else {
            // 즐겨찾기 추가
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setPlace(place);
            favoriteRepository.save(favorite);
            return true;
        }
    }

    // 내 즐겨찾기 목록 조회
    public List<FavoriteResponse> getMyFavorites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요!"));

        return favoriteRepository.findByUserId(user.getId())
                .stream()
                .map(FavoriteResponse::new)
                .collect(Collectors.toList());
    }

    // 즐겨찾기 여부 확인
    public boolean isFavorite(Long placeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요!"));

        return favoriteRepository.existsByUserIdAndPlaceId(user.getId(), placeId);
    }
}