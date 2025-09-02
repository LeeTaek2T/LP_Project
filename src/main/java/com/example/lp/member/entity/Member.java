package com.example.lp.member.entity;

import com.example.lp.cart.entity.Cart;
import com.example.lp.order.entity.Order;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderList;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }
}
