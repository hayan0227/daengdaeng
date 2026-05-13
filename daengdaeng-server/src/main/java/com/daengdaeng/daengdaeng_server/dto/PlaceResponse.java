package com.daengdaeng.daengdaeng_server.dto;

import java.util.List;
import java.util.Arrays;
import com.daengdaeng.daengdaeng_server.entity.Place;

import lombok.Getter;

@Getter
public class PlaceResponse {

    private Long id;
    private String name;
    private String category;
    private String description;
    private String address;
    private String phone;
    private String hours;
    private String imageUrl;
    private Double rating;
    private Double lat;
    private Double lng;
    private List<String> tags;

    public PlaceResponse(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.category = place.getCategory();
        this.description = place.getDescription();
        this.address = place.getAddress();
        this.phone = place.getPhone();
        this.hours = place.getHours();
        this.imageUrl = place.getImageUrl();
        this.rating = place.getRating();
        this.lat = place.getLat();
        this.lng = place.getLng();
        this.tags = place.getTags() != null && !place.getTags().isEmpty()
            ? Arrays.asList(place.getTags().split(","))
            : List.of();
    }
}