package com.example.lp.jwt.Util;

import io.jsonwebtoken.Claims;
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
    @Value("${jwt.issuer:lp-project}") private String issuer;
    @Value("${jwt.access-exp-ms:900000}")  private long accessExpMs;   // 15m
    @Value("${jwt.refresh-exp-ms:1209600000}") private long refreshExpMs; // 14d
    private final JwtParser jwtParser;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(issuer)      // ← 모든 파싱에 동일 적용
                .clockSkewSeconds(30)       // ← 시계 오차 허용
                .build();
    }



    public String createAccessToken(String userEmail, String role){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userEmail)
                .claim("role", role)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String subject, String jti) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setId(jti)                      // ★ jti 저장
                .claim("typ", "refresh")         // 구분용
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpMs))
                .signWith(secretKey)
                .compact();
    }
    public Claims parse(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)      // 검증 키 등록
                .requireIssuer(issuer)      // (선택) iss 강제
                .clockSkewSeconds(30)       // (선택) 시계 오차 허용
                .build()
                .parseSignedClaims(jwt)     // 서명된 JWT 파싱
                .getPayload();              // = Claims
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

    // ★ 여기만 쓰세요
    public String getEmail(String token){
        return jwtParser.parseSignedClaims(token).getPayload().getSubject();
    }
    public String getRole(String token){
        return jwtParser.parseSignedClaims(token).getPayload().get("role", String.class);
    }
    public String getJti(String token){
        return jwtParser.parseSignedClaims(token).getPayload().getId();
    }
    public boolean isExpired(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
