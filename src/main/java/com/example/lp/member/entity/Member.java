package com.example.lp.member.entity;

import com.example.lp.cart.entity.Cart;
import com.example.lp.order.entity.Order;
import com.example.lp.order.entity.OrderDetail;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "member")
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email",length = 50, nullable = false)
    private String email;

    @Column(name = "phone_number", length = 13, nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "postcode")
    private String postcode;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetailList;

    public Member() {}

    public Member(String email, String password, String phoneNumber, String role,
                  String state, OffsetDateTime createdAt, String userName) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    public Member(String email, String password, String phoneNumber, String userName) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = "ROLE_BUYER";
        this.createdAt = OffsetDateTime.now();
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
