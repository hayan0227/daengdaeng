package com.daengdaeng.daengdaeng_server.service;

import com.daengdaeng.daengdaeng_server.dto.AuthResponse;
import com.daengdaeng.daengdaeng_server.dto.LoginRequest;
import com.daengdaeng.daengdaeng_server.dto.SignupRequest;
import com.daengdaeng.daengdaeng_server.entity.User;
import com.daengdaeng.daengdaeng_server.repository.UserRepository;
import com.daengdaeng.daengdaeng_server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public AuthResponse signup(SignupRequest request) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용중인 이메일이에요!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setPetName(request.getPetName());
        user.setPetBreed(request.getPetBreed());
        user.setPetAge(request.getPetAge());
        user.setPetSize(request.getPetSize());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getNickname(), user.getEmail());
    }

    // 로그인
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일이에요!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸어요!");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getNickname(), user.getEmail());
    }
}