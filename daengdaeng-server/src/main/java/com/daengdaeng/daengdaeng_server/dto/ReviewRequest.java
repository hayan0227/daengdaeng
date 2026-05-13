package com.daengdaeng.daengdaeng_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewRequest {
    private Long placeId;
    private Integer stars;
    private String content;
}