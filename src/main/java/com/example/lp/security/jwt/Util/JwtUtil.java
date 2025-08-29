package com.example.lp.security.jwt.Util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    //loginId 반환 메서드
    public String getLoginEmailId(String token){
        return jwtParser.parseSignedClaims(token).getPayload().get("loginEmailId", String.class);
    }

    //role 반환 메서드
    public String getRole(String token){
        return jwtParser.parseSignedClaims(token).getPayload().get("role", String.class);
    }

    //토큰이 소멸(유효기간 만료)하였는지 검증 메서드
    public Boolean isExpired(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 토큰 생성 메서드
    public String createJwt(String loginEmailId, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("loginEmailId", loginEmailId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
