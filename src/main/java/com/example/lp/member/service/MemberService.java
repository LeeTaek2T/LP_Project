package com.example.lp.member.service;

import com.example.lp.jwt.dto.Response.AccessTokenResponse;
import com.example.lp.jwt.entity.RefreshToken;
import com.example.lp.jwt.service.RefreshTokenService;
import com.example.lp.member.dto.request.LoginRequest;
import com.example.lp.member.dto.request.SignUpRequest;
import com.example.lp.member.dto.response.MemberInfoResponse;
import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.jwt.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder,
                         RefreshTokenService refreshTokenRepository, RefreshTokenService refreshTokenService) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public boolean checkLoginIdDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void securitySignUp(SignUpRequest signUpRequest) {
        String encodePassword = passwordEncoder.encode(signUpRequest.password());
        Member member = new Member(signUpRequest.email(), encodePassword,
                signUpRequest.phoneNumber(), signUpRequest.userName());
        memberRepository.save(member);
    }


    @Transactional
    public AccessTokenResponse login(LoginRequest loginRequest, HttpServletRequest httpReq, HttpServletResponse httpRes ) {

        Member member = memberRepository.findByEmail((loginRequest.email()))
                .orElseThrow(() -> new RuntimeException());
        if(! passwordEncoder.matches(loginRequest.password(), member.getPassword())){
            return null;
        }

        String accessToken = jwtUtil.createAccessToken(member.getEmail(), member.getRole());
        String jti = UUID.randomUUID().toString();
        String refreshToken = jwtUtil.createRefreshToken(member.getEmail(), jti);

        var claims = jwtUtil.parse(refreshToken);
        java.time.Instant exp = claims.getExpiration().toInstant();
        refreshTokenService.registerRefreshToken(member.getEmail(), jti, exp);

        addRefreshCookie(httpRes, refreshToken);

        return new AccessTokenResponse("Bearer", accessToken);
    }


    @Transactional
    public AccessTokenResponse refresh(HttpServletRequest req, HttpServletResponse res) {
        // 0) 쿠키에서 refresh 토큰 추출 (컨트롤러는 모름)
        String refreshToken = extractRefreshCookie(req)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no refresh cookie"));

        // 1) JWT 검증 + 클레임 읽기
        var claims = jwtUtil.parse(refreshToken);       // 서명/만료 검증 (실패 시 예외)
        String email = claims.getSubject();
        String jti   = claims.getId();

        Long userId = memberRepository.findByEmail(email)
                .map(Member::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not found"));

        // 2) DB에 있는 활성(refresh)토큰 확인
        RefreshToken current = refreshTokenService.findActive(jti)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid/expired refresh"));

        // 3) 회전: 기존 revoke + 새 refresh 발급/저장
        String newJti     = java.util.UUID.randomUUID().toString();
        String newRefresh = jwtUtil.createRefreshToken(email, newJti);
        Instant newExp = jwtUtil.parse(newRefresh).getExpiration().toInstant();
        refreshTokenService.rotate(current, newJti, newExp);     // ← DB 업데이트/INSERT
        // 4) 새 access 발급
        String role = memberRepository.findById(userId).map(Member::getRole)
                .orElse("USER"); // 적절히 처리
        String access = jwtUtil.createAccessToken(email, role);

        // 5) 쿠키 교체 (서비스가 직접 세팅)
        setRefreshCookie(res, newRefresh);

        return new AccessTokenResponse("Bearer", access);
    }

    @Transactional
    public void logoutAll(Authentication member, HttpServletResponse res) {
        // 컨트롤러에 @AuthenticationPrincipal 안 받아도 됨. 서비스가 직접 읽음
        if (member == null || !member.isAuthenticated() || "anonymousUser".equals(member.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        //뭐를 반환하는지 확인해야함
        String userEmail = member.getName();

        refreshTokenService.revokeAllForUser(userEmail); // 모든 기기 로그아웃
        clearRefreshCookie(res);                // 쿠키 삭제
    }

    public MemberInfoResponse getMemberInfo(Authentication member) {
        Member registerdMember = memberRepository.findByEmail(member.getName())
                .orElseThrow(() -> new RuntimeException());
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(registerdMember.getUserName(),
                registerdMember.getEmail(), registerdMember.getPhoneNumber(), registerdMember.getAddress(),
                registerdMember.getAddressDetail(), registerdMember.getPostcode());
        return memberInfoResponse;
    }

    public void registerMemberHomeAddress(Authentication member) {
        Member registeredMember = memberRepository.findByEmail(member.getName())
                .orElseThrow(() -> new RuntimeException());
        registeredMember.registerHomeAddress(registeredMember.getAddress(),
                registeredMember.getAddressDetail(), registeredMember.getPostcode());
    }


    private void addRefreshCookie(HttpServletResponse res, String refreshJwt) {
        // SameSite 설정은 ResponseCookie가 편리
        org.springframework.http.ResponseCookie cookie = org.springframework.http.ResponseCookie.from("refresh_token", refreshJwt)
                .httpOnly(true)
                .secure(true)                    // HTTPS
                .sameSite("Lax")                 // 크로스 도메인이면 "None" + secure
                .path("/api/member/refresh")           // 재발급 경로로만 전송
                .maxAge(java.time.Duration.ofDays(14))
                .build();
        res.addHeader(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private Optional<String> extractRefreshCookie(HttpServletRequest req) {
        var cookies = req.getCookies();
        if (cookies == null) return Optional.empty();
        for (var c : cookies) {
            if ("refresh_token".equals(c.getName())) return Optional.ofNullable(c.getValue());
        }
        return Optional.empty();
    }

    private void setRefreshCookie(HttpServletResponse res, String refreshJwt) {
        // ResponseCookie로 SameSite/HttpOnly/Path 쉽게 설정
        var cookie = org.springframework.http.ResponseCookie.from("refresh_token", refreshJwt)
                .httpOnly(true)
                .secure(true)                 // HTTPS 전제
                .sameSite("Lax")              // 크로스도메인이면 "None" + secure
                .path("/api/member/refresh")        // 이 경로로만 전송되게
                .maxAge(java.time.Duration.ofDays(14))
                .build();
        res.addHeader(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void clearRefreshCookie(HttpServletResponse res) {
        var cookie = org.springframework.http.ResponseCookie.from("refresh_token", "")
                .httpOnly(true).secure(true).sameSite("Lax").path("/auth/refresh")
                .maxAge(java.time.Duration.ZERO) // 즉시 만료
                .build();
        res.addHeader(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
