package com.example.lp.member.dto.request;

public record SignUpRequest(String email,
                            String password,
                            String passwordCheck,
                            String phoneNumber,
                            String userName) {
}
