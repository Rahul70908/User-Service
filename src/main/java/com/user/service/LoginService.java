package com.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.exception.ApplicationException;
import com.user.model.request.LoginDto;
import com.user.model.response.ApiResponse;
import com.user.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    public ApiResponse login(LoginDto loginDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
        var user = userRepository.findByName(loginDto.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found!!!"));
        String token = jwtService.generateToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveToken(user, token);
        var refreshToken = jwtService.generateRefreshToken(user);
        return ApiResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .timeStamp(Instant.now())
                .httpStatus(HttpStatus.OK)
                .message("Login Successful!!")
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userName;
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String refreshToken = authHeader.substring(7);
        userName = jwtService.extractUserName(refreshToken);
        if (StringUtils.isNotBlank(userName)) {
            var userDetails = userRepository.findByName(userName)
                    .orElseThrow(() -> new ApplicationException("User Not Found!!"));
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                tokenService.revokeAllUserTokens(userDetails);
                tokenService.saveToken(userDetails, accessToken);
                var apiResponse = ApiResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .timeStamp(Instant.now())
                        .message("Token Generation Successfully!!!")
                        .build();
                objectMapper.writeValue(response.getOutputStream(), apiResponse);
            }
        }

    }
}
