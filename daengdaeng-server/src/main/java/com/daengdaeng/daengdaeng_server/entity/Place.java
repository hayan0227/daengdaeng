package com.daengdaeng.daengdaeng_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "places")
@Getter @Setter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;
    private String description;
    private String address;
    private String phone;
    private String hours;
    private String tags;
    private String imageUrl;

    @Column(columnDefinition = "DOUBLE DEFAULT 0")
    private Double rating;
    private Double lat;
    private Double lng;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}