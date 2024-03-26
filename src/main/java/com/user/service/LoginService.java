package com.user.service;

import com.user.model.request.LoginDto;
import com.user.model.response.ApiResponse;
import com.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenService tokenService;

    public ApiResponse login(LoginDto loginDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
        var user = userRepository.findByName(loginDto.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found!!!"));
        String token = jwtService.generateToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveToken(user, token);
        return ApiResponse.builder()
                .token(token)
                .timeStamp(Instant.now())
                .httpStatus(HttpStatus.OK)
                .message("Login Successful!!")
                .build();
    }
}
