package com.example.lp.tosspayment.controller;

import com.example.lp.tosspayment.dto.request.ConfirmRequest;
import com.example.lp.tosspayment.dto.request.PaymentPreRequest;
import com.example.lp.tosspayment.dto.response.ConfirmResponse;
import com.example.lp.tosspayment.dto.response.TossConfirmResponse;
import com.example.lp.tosspayment.dto.response.PaymentResponse;
import com.example.lp.tosspayment.dto.response.PaymentPreResponse;
import com.example.lp.order.service.TossPaymentService;
import com.example.lp.security.config.TossConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api")
public class TossPaymentController {
    private final TossPaymentService tossPaymentService;
    private final TossConfig tossConfig;
    private final RestTemplate tossRestTemplate;

    public TossPaymentController(TossPaymentService tossPaymentService, TossConfig tossConfig,
                                 RestTemplate tossRestTemplate) {
        this.tossPaymentService = tossPaymentService;
        this.tossConfig = tossConfig;
        this.tossRestTemplate = tossRestTemplate;
    }

    @PostMapping("/order/{dbOrderId}/toss/paymentPre")
    public ResponseEntity<PaymentPreResponse> savePaymentPre(@RequestBody PaymentPreRequest paymentPreRequest,
                                                             @PathVariable long dbOrderId) {
        PaymentPreResponse paymentPreResponse = tossPaymentService.savePaymentPre(paymentPreRequest, dbOrderId);
        return ResponseEntity.ok(paymentPreResponse);
    }

    @PostMapping(value = "/toss/confirm")
    public ResponseEntity<ConfirmResponse> confirm(@RequestBody ConfirmRequest confirmRequest) {
        String url = tossConfig.getBaseUrl() + "/v1/payments/confirm";

        ResponseEntity<TossConfirmResponse> res = tossRestTemplate.postForEntity(url, confirmRequest, TossConfirmResponse.class);
        TossConfirmResponse base = res.getBody();
        TossConfirmResponse tossConfirmResponse = new TossConfirmResponse(base.orderId(), base.paymentKey(), base.totalAmount(),
                base.method(), base.status(), OffsetDateTime.now());

        ConfirmResponse confirmResponse = tossPaymentService.confirm(tossConfirmResponse);

        return ResponseEntity
                .status(res.getStatusCode())
                .body(confirmResponse);
    }

    @GetMapping(value = "/toss/confirm/{paymentId}")
    public ResponseEntity<PaymentResponse> getConfirmInfoById(@PathVariable Long paymentId) {
        PaymentResponse paymentResponse = tossPaymentService.getConfirmInfoById(paymentId);
        return ResponseEntity.ok(paymentResponse);
    }
}
