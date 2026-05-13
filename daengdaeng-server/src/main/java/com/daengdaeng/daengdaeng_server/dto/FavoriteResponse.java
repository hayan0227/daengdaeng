package com.daengdaeng.daengdaeng_server.dto;

import lombok.Getter;
import java.time.LocalDateTime;

import com.daengdaeng.daengdaeng_server.entity.Favorite;

@Getter
public class FavoriteResponse {

    private Long id;
    private Long placeId;
    private String placeName;
    private String placeAddress;
    private String category;
    private String imageUrl;
    private String tags;
    private String phone;
    private String hours;
    private Double rating;
    private Double lat;
    private Double lng;
    private LocalDateTime createdAt;

    public FavoriteResponse(Favorite favorite) {
        this.id = favorite.getId();
        this.placeId = favorite.getPlace().getId();
        this.placeName = favorite.getPlace().getName();
        this.placeAddress = favorite.getPlace().getAddress();
        this.category = favorite.getPlace().getCategory();
        this.imageUrl = favorite.getPlace().getImageUrl();
        this.tags = favorite.getPlace().getTags();
        this.phone = favorite.getPlace().getPhone();
        this.hours = favorite.getPlace().getHours();
        this.rating = favorite.getPlace().getRating();
        this.lat = favorite.getPlace().getLat();
        this.lng = favorite.getPlace().getLng();
        this.createdAt = favorite.getCreatedAt();
    }
}