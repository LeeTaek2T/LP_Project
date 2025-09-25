package com.example.lp.member.dto.response;

public record MemberInfoResponse(String userName,
                                 String email,
                                 String phoneNumber,
                                 String address,
                                 String addressDetail,
                                 String postcode) {
}
