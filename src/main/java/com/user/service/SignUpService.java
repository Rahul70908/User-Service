package com.user.service;

import com.user.entity.User;
import com.user.mapper.UserMapper;
import com.user.model.request.SignUpDto;
import com.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public User register(SignUpDto signUpDto) {
        User user = userMapper.formUser(signUpDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
