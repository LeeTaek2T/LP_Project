package com.example.lp.order;
import com.example.lp.member.entity.Member;
import com.example.lp.member.repository.MemberRepository;
import com.example.lp.order.dto.request.OrderProductInfo;
import com.example.lp.order.dto.request.OrderRequest;
import com.example.lp.order.service.OrderService;
import com.example.lp.product.entity.Product;
import com.example.lp.product.entity.ProductSku;
import com.example.lp.product.repository.ProductRepository;
import com.example.lp.product.repository.ProductSkuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.time.Instant;
import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class OrderServiceTest {

//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ProductSkuRepository productSkuRepository;
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    private Authentication auth;
//    private OrderRequest orderRequest;
//    private final Long skuId = 1L;
//    private final String redisHashKey = "eventItem:detail:" + skuId;
//    @Autowired
//    private JdbcTemplate jdbc;
//    @Autowired
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setup() {
//        Member member = new Member("b1@naver.com","$2a$10$x.szEPU5KDfwMoTUnMZyP.GJwycI9RGKyCWeYLDKQ2pXQAIxAIaA.",
//                "01012345678","LT");
//        memberRepository.saveAndFlush(member);
//        auth = new UsernamePasswordAuthenticationToken(
//                member.getEmail(), // principal에 이메일 넣기
//                null,
//                Collections.emptyList()
//        );
//        Product product = new Product("후드티",10000L, "coverImageUrl","상의");
//        productRepository.save(product);
//        ProductSku productSku = new ProductSku("black","XL",1000L,product);
//        productSkuRepository.save(productSku);
//
//
//        OrderProductInfo orderProductInfo = new OrderProductInfo("후드티",10000L,1L,"XL","black",skuId,"comverImageUrl");
//        orderRequest = new OrderRequest(10000L, List.of(orderProductInfo));
//
//        // 4) Redis: 해시 초기화 (eventItem:detail:{skuId} 의 "quantity" 필드)
//        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
//        hashOps.put(redisHashKey, "quantity", String.valueOf(1000));
//    }
//
//    /**
//     * 동시성 테스트: 다수 스레드에서 동시에 createOrder 호출
//     */
//    @Test
//    void testCreateOrder_concurrent_redis() throws InterruptedException {
//        int threadCount = 1000;
//        ExecutorService executor = Executors.newFixedThreadPool(1000); // 스레드풀 크기 (환경에 맞게 조절)
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        Instant start = Instant.now();
//        for (int i = 0; i < threadCount; i++) {
//            executor.submit(() -> {
//                try {
//                    orderService.createOrder(auth, orderRequest);
//                }catch (Exception e) {
//                    e.printStackTrace(); // 전체 스택 트레이스 출력
//                    System.out.println("주문 실패: " + e); // e.toString() → 예외 클래스 + 메시지 출력
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        latch.await();
//        executor.shutdown();
//        Instant end = Instant.now();
//
//        System.out.println("총 소요시간: " + Duration.between(start, end).toMillis() + " ms");
//
//        // 결과 확인: Redis 및 DB 상태 출력
//        String redisQty = (String) redisTemplate.opsForHash().get(redisHashKey, "quantity");
//        System.out.println("Redis 최종 quantity: " + redisQty);
//
//        ProductSku sku = productSkuRepository.findById(skuId).orElseThrow(()->new RuntimeException("테스트 sku아보임"));
//        System.out.println("DB 최종 quantity: " + sku.getQuantity());
////        // assert로 테스트 통과 여부 확인
//        assertThat(redisQty).isEqualTo("0");
////        assertThat(sku.getQuantity()).isEqualTo(0);
//    }
//
//    /**
//     * 동시성 테스트: 다수 스레드에서 동시에 createOrder 호출
//     */
//    @Test
//    void testCreateOrder_concurrent_DB() throws InterruptedException {
//        int threadCount = 1000;
//        ExecutorService executor = Executors.newFixedThreadPool(20); // 스레드풀 크기 (환경에 맞게 조절)
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        Instant start = Instant.now();
//        for (int i = 0; i < threadCount; i++) {
//            executor.submit(() -> {
//                try {
//                    orderService.createOrder(auth, orderRequest);
//                }catch (Exception e) {
//                    e.printStackTrace(); // 전체 스택 트레이스 출력
//                    System.out.println("주문 실패: " + e); // e.toString() → 예외 클래스 + 메시지 출력
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        latch.await();
//        executor.shutdown();
//        Instant end = Instant.now();
//
//        System.out.println("총 소요시간: " + Duration.between(start, end).toMillis() + " ms");
//
//
//        Produ ctSku sku = productSkuRepository.findById(skuId).orElseThrow(()->new RuntimeException("테스트 sku아보임"));
//        System.out.println("DB 최종 quantity: " + sku.getQuantity());
////        // assert로 테스트 통과 여부 확인
//        assertThat(sku.getQuantity()).isEqualTo(0);
//    }
}
