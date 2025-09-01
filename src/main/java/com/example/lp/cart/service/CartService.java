package com.example.lp.cart.service;

import com.example.lp.cart.entity.Cart;
import com.example.lp.cart.repository.CartRepository;
import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductSkuRepository productSkuRepository;
    private MemberRepository memberRepository;

    public CartService(CartRepository cartRepository, ProductSkuRepository productSkuRepository) {
        this.cartRepository = cartRepository;
        this.productSkuRepository = productSkuRepository;
        this.memberRepository = memberRepository;
    }

    public Long addProduct(Authentication auth, Long productSkuId) {
        ProductSku productSku = productSkuRepository.findById(productSkuId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException());
        Cart cart = new Cart(member, productSku);
        Cart savedCart = cartRepository.save(cart);
        return savedCart.getProductSku().getId();
    }
}
