package com.daengdaeng.daengdaeng_server.dto;

import com.daengdaeng.daengdaeng_server.entity.Review;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReviewResponse {

    private Long id;
    private Long placeId;
    private String placeName;
    private String nickname;
    private Integer stars;
    private String content;
    private LocalDateTime createdAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.placeId = review.getPlace().getId();
        this.placeName = review.getPlace().getName();
        this.nickname = review.getUser().getNickname();
        this.stars = review.getStars();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}