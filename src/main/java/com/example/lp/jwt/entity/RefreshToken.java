package com.example.lp.jwt.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    @Column(name = "token_id", nullable = false, unique = true)
    private String tokenId;
    private boolean revoked = false;
    private Instant expiresAt;
    private String replacedByTokenId;

    public RefreshToken() {}

    public RefreshToken(String userEmail, String tokenId, Instant expiresAt) {
        this.userEmail = userEmail;
        this.tokenId = tokenId;
        this.expiresAt = expiresAt;
    }

    public RefreshToken(Long id, String userEmail, String tokenId, boolean revoked, Instant expiresAt, String replacedByTokenId) {
        this.id = id;
        this.userEmail = userEmail;
        this.tokenId = tokenId;
        this.revoked = revoked;
        this.expiresAt = expiresAt;
        this.replacedByTokenId = replacedByTokenId;
    }

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTokenId() {
        return tokenId;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public String getReplacedByTokenId() {
        return replacedByTokenId;
    }
}
