package com.user.service;

import com.user.entity.User;
import com.user.model.Role;
import com.user.model.request.SignUpDto;
import com.user.model.response.ApiResponse;
import com.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final JwtService jwtService;

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse register(SignUpDto signUpDto) {
        var user = User.builder()
                .name(signUpDto.getUserName())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        tokenService.saveToken(savedUser, jwtToken);
        return ApiResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(user.getUsername().concat(" Registered Successfully!!"))
                .timeStamp(Instant.now())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
