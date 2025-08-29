package com.example.lp.member.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "member")
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email",length = 50, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", length = 13, nullable = false)
    private String phoneNumber;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "is_seller", nullable = false)
    private Boolean isSeller;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_name", nullable = false)
    private String userName;

    public Member() {}

    public Member(String email, String password, String phoneNumber, Boolean isAdmin, Boolean isSeller,
                  String state, LocalDateTime createdAt, String userName) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.isSeller = isSeller;
        this.state = state;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    public Member(String email, String password, String phoneNumber, String userName) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isAdmin = false;
        this.isSeller = false;
        this.state = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.userName = userName;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }
}
