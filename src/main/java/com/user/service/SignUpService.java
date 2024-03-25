package com.user.service;

import com.user.entity.Role;
import com.user.entity.User;
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

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse register(SignUpDto signUpDto) {
        User user = User.builder()
                .name(signUpDto.getUserName())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return ApiResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(user.getUsername().concat(" Registered Successfully!!"))
                .timeStamp(Instant.now())
                .build();
    }
}
