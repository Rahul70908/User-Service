package com.user.controller;

import com.user.entity.User;
import com.user.model.request.SignUpDto;
import com.user.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/register")
    private ResponseEntity<User> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(signUpService.register(signUpDto));
    }
}
