package com.daengdaeng.daengdaeng_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
    private String email;
    private String password;
    private String nickname;
    private String petName;
    private String petBreed;
    private Integer petAge;
    private String petSize;
}