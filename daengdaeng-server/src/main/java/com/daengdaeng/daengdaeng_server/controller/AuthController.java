package com.daengdaeng.daengdaeng_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daengdaeng.daengdaeng_server.dto.AuthResponse;
import com.daengdaeng.daengdaeng_server.dto.LoginRequest;
import com.daengdaeng.daengdaeng_server.dto.SignupRequest;
import com.daengdaeng.daengdaeng_server.dto.UserResponse;
import com.daengdaeng.daengdaeng_server.entity.User;
import com.daengdaeng.daengdaeng_server.repository.UserRepository;
import com.daengdaeng.daengdaeng_server.service.AuthService;
import com.daengdaeng.daengdaeng_server.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean available = !userRepository.existsByEmail(email);
        return ResponseEntity.ok(java.util.Map.of("available", available));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(HttpServletRequest request) {
        String email = getEmailFromRequest(request);
        if (email == null) return ResponseEntity.status(401).build();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없어요"));
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(HttpServletRequest request, @RequestBody SignupRequest body) {
        String email = getEmailFromRequest(request);
        if (email == null) return ResponseEntity.status(401).build();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없어요"));
        if (body.getNickname() != null) user.setNickname(body.getNickname());
        if (body.getPetName() != null) user.setPetName(body.getPetName());
        if (body.getPetBreed() != null) user.setPetBreed(body.getPetBreed());
        if (body.getPetAge() != null) user.setPetAge(body.getPetAge());
        if (body.getPetSize() != null) user.setPetSize(body.getPetSize());
        userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(user));
    }

    private String getEmailFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) return null;
        return jwtUtil.getEmailFromToken(token);
    }
}