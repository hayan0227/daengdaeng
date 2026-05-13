package com.daengdaeng.daengdaeng_server.dto;

import com.daengdaeng.daengdaeng_server.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private String petName;
    private String petBreed;
    private Integer petAge;
    private String petSize;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.petName = user.getPetName();
        this.petBreed = user.getPetBreed();
        this.petAge = user.getPetAge();
        this.petSize = user.getPetSize();
    }
}