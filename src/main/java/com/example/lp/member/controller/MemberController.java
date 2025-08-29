package com.example.lp.member.controller;

import com.example.lp.member.dto.request.LoginRequest;
import com.example.lp.member.dto.request.SignUpRequest;
import com.example.lp.member.service.MemberService;
import com.example.lp.security.jwt.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/signUp")
    public ResponseEntity<String> singUp(@RequestBody SignUpRequest signUpRequest) {
        if (memberService.checkLoginIdDuplicate(signUpRequest.email())){
            return ResponseEntity.badRequest().body("ID alreay exist");
        }
        if(!signUpRequest.password().equals(signUpRequest.passwordCheck())){
            return ResponseEntity.badRequest().body("password check error");
        }
        memberService.securitySignUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PostMapping("/member/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = memberService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/member/info")
    public ResponseEntity<String> getMemberInfo(Authentication auth) {
        String response = memberService.getMemberInfo(auth);
        return ResponseEntity.ok(response);
    }
}
