package com.example.lp.jwt.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomMemberDetail implements UserDetails {
    private final String userEmail;
    private final String role;

    public CustomMemberDetail(String userEmail, String role) {
        this.userEmail = userEmail;
        this.role = role;
    }

    // 계정의 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // 계정의 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // 자격 증명의 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }
}
