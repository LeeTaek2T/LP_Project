package com.example.lp.jwt.repository;

import com.example.lp.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenIdAndRevokedFalse(String tokenId);

    // 단일 세션 정책
    @Modifying
    @Query("update RefreshToken rt set rt.revoked=true " +
            "where rt.userEmail=:userEmail and rt.revoked=false")
    void revokeAllForUser(@Param("userEmail") String userEmail);

}
