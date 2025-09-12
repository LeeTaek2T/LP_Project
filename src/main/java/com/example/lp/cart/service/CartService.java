package com.example.lp.cart.service;

import com.example.lp.cart.dto.reqeust.CartRequest;
import com.example.lp.cart.dto.response.CartResponse;
import com.example.lp.cart.entity.Cart;
import com.example.lp.cart.repository.CartRepository;
import com.example.lp.event.entity.EventItem;
import com.example.lp.event.repository.EventItemRepository;
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
    private EventItemRepository eventItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       ProductSkuRepository productSkuRepository, MemberRepository memberRepository,
                       EventItemRepository eventItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.productSkuRepository = productSkuRepository;
        this.memberRepository = memberRepository;
        this.eventItemRepository = eventItemRepository;
    }

    public Long addProduct(Authentication auth, CartRequest cartRequest) {
        Product product = productRepository.findById(cartRequest.productId())
                .orElseThrow(() -> new RuntimeException());
        ProductSku productSku = productSkuRepository.findByProductAndId(product, cartRequest.productSkuId())
                .orElseThrow(() -> new RuntimeException("<UNK>"));
        Member member = memberRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException());
        Cart cart = new Cart(member, cartRequest.quantity(), productSku);
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
            ProductSku productSku = productSkuRepository.findById((cart.getProductSku().getId()))
                    .orElseThrow(() -> new RuntimeException("<UNK>"));
            Product product = productSku.getProduct();
            Long buyPrice;
            if (product.getSaled()){
                EventItem eventItem = eventItemRepository.findByProduct(product)
                        .orElseThrow(() -> new RuntimeException());
                buyPrice = eventItem.getSalePrice();
            }else{
                buyPrice = product.getPrice();
            }

            CartResponse cartResponse = new CartResponse(product.getName(), buyPrice, cart.getQuantity(),
                    product.getCoverImageUrl(), productSku.getId(), productSku.getSize(), productSku.getColor());
            cartResponseList.add(cartResponse);
        }
        return cartResponseList;
    }
}
