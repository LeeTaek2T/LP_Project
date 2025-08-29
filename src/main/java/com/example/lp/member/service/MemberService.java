package com.example.lp.member.service;

import com.example.lp.member.dto.request.LoginRequest;
import com.example.lp.member.dto.request.SignUpRequest;
import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.security.jwt.Util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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

    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail((loginRequest.email()))
                .orElseThrow(() -> new RuntimeException());
        if(! passwordEncoder.matches(loginRequest.password(), member.getPassword())){
            return null;
        }
        String token = jwtUtil.createJwt(member.getEmail(), "ADMIN", 1000 * 60 * 60L);
        return token;
    }

    public String getMemberInfo(Authentication auth) {
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException());
        return member.getEmail();
    }
}
