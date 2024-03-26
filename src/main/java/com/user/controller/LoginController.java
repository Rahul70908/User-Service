package com.user.controller;

import com.user.model.request.LoginDto;
import com.user.model.response.ApiResponse;
import com.user.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(loginService.login(loginDto));
    }

    @PostMapping("/refreshToken")
    private void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginService.refreshToken(request, response);
    }
}
