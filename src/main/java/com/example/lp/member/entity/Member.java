package com.example.lp.member.entity;

import com.example.lp.cart.entity.Cart;
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

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "is_seller", nullable = false)
    private Boolean isSeller;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    public Member() {}

    public Member(String email, String password, String phoneNumber, String role, Boolean isSeller,
                  String state, LocalDateTime createdAt, String userName) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isSeller = isSeller;
        this.state = state;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    public Member(String email, String password, String phoneNumber, String userName) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = "ROLE_BUYER";
        this.isSeller = false;
        this.state = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRole() {
        return this.role;
    }
}
