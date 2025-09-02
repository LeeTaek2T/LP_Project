package com.example.lp.cart.service;

import com.example.lp.cart.dto.reqeust.CartRequest;
import com.example.lp.cart.dto.response.CartResponse;
import com.example.lp.cart.entity.Cart;
import com.example.lp.cart.repository.CartRepository;
import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductRepository;
import com.example.lp.product.repository.ProductSkuRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private ProductSkuRepository productSkuRepository;
    private MemberRepository memberRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       ProductSkuRepository productSkuRepository, MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.productSkuRepository = productSkuRepository;
        this.memberRepository = memberRepository;
    }

    public Long addProduct(Authentication auth, Long productId, Long productSkuId, CartRequest cartRequest) {
        ProductSku productSku = productSkuRepository.findById(productSkuId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException());
        Cart cart = new Cart(member, cartRequest.quantity() ,product, productSku);
        Cart savedCart = cartRepository.save(cart);
        return savedCart.getProductSku().getId();
    }

    public List<CartResponse> getCart(Authentication auth) {
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        List<Cart> cartList = cartRepository.findByMember(member);
        List<CartResponse> cartResponseList = convertToCartResponseList(cartList);
        return cartResponseList;
    }

    private List<CartResponse> convertToCartResponseList(List<Cart> cartList) {
        List<CartResponse> cartResponseList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = productRepository.findById(cart.getProduct().getId())
                    .orElseThrow(()-> new RuntimeException("<UNK>"));
            ProductSku productSku = productSkuRepository.findById((cart.getProductSku().getId()))
                    .orElseThrow(() -> new RuntimeException("<UNK>"));
            CartResponse cartResponse = new CartResponse(product.getId(),
                    productSku.getId(), product.getName(), productSku.getColor(), productSku.getSize(),
                    cart.getQuantity(), productSku.getPrice(), productSku.getInActive());
            cartResponseList.add(cartResponse);
        }
        return cartResponseList;
    }
}
