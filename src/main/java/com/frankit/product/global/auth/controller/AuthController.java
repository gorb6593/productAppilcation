package com.frankit.product.global.auth.controller;

import com.frankit.product.global.auth.dto.request.LoginRequest;
import com.frankit.product.global.auth.dto.request.SignupRequest;
import com.frankit.product.global.auth.dto.response.LoginResponse;
import com.frankit.product.global.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("login request: {}", request);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
        log.info("signup request: {}", request);
        authService.signup(request);
        return ResponseEntity.ok().build();
    }
}
