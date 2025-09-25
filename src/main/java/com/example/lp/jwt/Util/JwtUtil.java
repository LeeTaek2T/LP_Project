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
    private final SecretKey secretKey; // JWT 서명에 사용할 비밀 키
    @Value("${jwt.issuer:lp-project}") private String issuer; // 발급자 정보
    @Value("${jwt.access-exp-ms:900000}")  private long accessExpMs;   // Access Token 유효기간 (15분)
    @Value("${jwt.refresh-exp-ms:1209600000}") private long refreshExpMs; // Refresh Token 유효기간 (14일)
    private final JwtParser jwtParser; // 미리 설정된 JWT 파서

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        // 1. 비밀 키 생성
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        // 2. JWT 파서(검증기) 생성
        this.jwtParser = Jwts.parser()
                .verifyWith(secretKey)      // 이 키로 서명을 검증
                .requireIssuer(issuer)      // 발급자가 일치해야 함
                .clockSkewSeconds(30)       // 서버 간 시간 오차 30초 허용
                .build();
    }



    public String createAccessToken(String userEmail, String role){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userEmail)
                .claim("ROLE_", role)
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
                .setId(jti)
                .claim("typ", "refresh")
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpMs))
                .signWith(secretKey)
                .compact();
    }
    public Claims parse(String jwt) { // String 형태의 JWT를 입력받음
        return Jwts.parser() // 1. JWT 파서(해석기) 생성 시작
                .verifyWith(secretKey)      // 2. 서명 검증: 이 secretKey로 서명된 토큰이 맞는지 확인 (위조 방지)
                .requireIssuer(issuer)      // 3. 발급자 확인: 토큰을 발급한 주체('iss' 클레임)가 서버에서 설정한 'issuer'와 일치하는지 확인
                .clockSkewSeconds(30)       // 4. 시간 오차 허용: 서버 간 시간 차이로 인한 오류를 막기 위해 30초의 시간 오차를 허용
                .build()                    // 5. 설정 완료 후 파서 빌드
                .parseSignedClaims(jwt)     // 6. 입력받은 JWT 문자열 파싱 및 검증 실행 (이 단계에서 유효하지 않으면 예외 발생)
                .getPayload();              // 7. 검증 통과 시, 토큰의 payload 부분(실제 정보가 담긴 곳)을 Claims 객체로 반환
    }

    public String getEmail(String token){
        return jwtParser.parseSignedClaims(token).getPayload().getSubject();
    }
    public String getRole(String token){
        return jwtParser.parseSignedClaims(token).getPayload().get("ROLE_", String.class);
    }
    public String getJti(String token){
        return jwtParser.parseSignedClaims(token).getPayload().getId();
    }
    public boolean isExpired(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
