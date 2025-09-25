package com.example.lp.jwt.service;

import com.example.lp.jwt.entity.RefreshToken;
import com.example.lp.jwt.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void registerRefreshToken(String userEmail, String jti, Instant exp) {
        RefreshToken refreshToken = new RefreshToken(userEmail, jti, exp);
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findActive(String jti) {
        return refreshTokenRepository.findByTokenIdAndRevokedFalse(jti)       // 1) jti 일치 + revoked=false 인 토큰 조회
                .filter(rt -> rt.getExpiresAt().isAfter(Instant.now())); // 2) 만료 시각이 지금 이후인지(미만료) 추가 체크
    }

    public void rotate(RefreshToken current, String newJti, Instant newExp) {
        RefreshToken currentRefreshToken = new RefreshToken(current.getId(), current.getUserEmail(),
                current.getTokenId(), true, current.getExpiresAt(), newJti);
        refreshTokenRepository.save(currentRefreshToken);

        RefreshToken newRefreshToken = new RefreshToken(current.getUserEmail(),  newJti, newExp);
        refreshTokenRepository.save(newRefreshToken);
    }

    public void revokeAllForUser(String userEmail) {
        refreshTokenRepository.revokeAllForUser(userEmail);
    }

}
