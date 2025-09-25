package com.example.lp.member.controller;

import com.example.lp.jwt.dto.Response.AccessTokenResponse;
import com.example.lp.member.dto.request.LoginRequest;
import com.example.lp.member.dto.request.SignUpRequest;
import com.example.lp.member.dto.response.MemberInfoResponse;
import com.example.lp.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest req,
                                                     HttpServletRequest httpReq,
                                                     HttpServletResponse httpRes) {
        AccessTokenResponse body = memberService.login(req, httpReq, httpRes);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/member/refresh")
    public ResponseEntity<AccessTokenResponse> refresh(HttpServletRequest req, HttpServletResponse res) {
        var body = memberService.refresh(req, res);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/member/logoutAll")
    public ResponseEntity<Void> logoutAll(Authentication member, HttpServletResponse res) {
        memberService.logoutAll(member, res);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/info")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(Authentication member) {
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(member);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PutMapping("/member/info")
    public ResponseEntity<Void> registerMemberHomeAddress(Authentication member){
        memberService.registerMemberHomeAddress(member);
        return ResponseEntity.ok().build();
    }

}
