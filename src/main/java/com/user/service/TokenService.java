package com.user.service;

import com.user.entity.Token;
import com.user.entity.User;
import com.user.model.TokenType;
import com.user.repo.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false).revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        var validToken = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validToken.isEmpty()) {
            return;
        }
        validToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validToken);
    }

}
