package com.frankit.product.global.auth.service;

import com.frankit.product.global.auth.dto.request.LoginRequest;
import com.frankit.product.global.auth.dto.request.SignupRequest;
import com.frankit.product.global.auth.dto.response.LoginResponse;
import com.frankit.product.global.auth.entity.Account;
import com.frankit.product.global.auth.repository.AccountRepository;
import com.frankit.product.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(account.getEmail());
        log.info("token : {}", token);
        return LoginResponse.from(token);
    }

    @Transactional
    public void signup(SignupRequest request) {
        if (accountRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Account account = Account.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        accountRepository.save(account);
    }
}
