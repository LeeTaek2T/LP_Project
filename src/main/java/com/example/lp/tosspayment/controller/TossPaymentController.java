package com.example.lp.tosspayment.controller;

import com.example.lp.tosspayment.dto.request.*;
import com.example.lp.tosspayment.dto.response.*;
import com.example.lp.tosspayment.service.TossPaymentService;
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

    @PostMapping("/order/{orderId}/toss/paymentPre")
    public ResponseEntity<PaymentPreResponse> savePaymentPre(@RequestBody PaymentPreRequest paymentPreRequest,
                                                             @PathVariable long orderId) {
        PaymentPreResponse paymentPreResponse = tossPaymentService.savePaymentPre(paymentPreRequest, orderId);
        return ResponseEntity.ok(paymentPreResponse);
    }

    @PostMapping(value = "/toss/confirm")
    public ResponseEntity<ConfirmResponse> confirm(@RequestBody ConfirmRequest confirmRequest) {
        String url = tossConfig.getBaseUrl() + "/v1/payments/confirm";

        TossConfirmRequest tossConfirmRequest = new TossConfirmRequest(confirmRequest.tossPaymentKey(),
                confirmRequest.tossOrderId(), confirmRequest.amount());
        ResponseEntity<TossConfirmResponse> res = tossRestTemplate.postForEntity(url, tossConfirmRequest,
                TossConfirmResponse.class);
        TossConfirmResponse base = res.getBody();
        TossConfirmResponse tossConfirmResponse = new TossConfirmResponse(base.orderId(), base.paymentKey(), base.totalAmount(),
                base.method(), base.status(), OffsetDateTime.now());

        ConfirmResponse confirmResponse = tossPaymentService.confirm(tossConfirmResponse);

        return ResponseEntity
                .status(res.getStatusCode())
                .body(confirmResponse);
    }

    //orderId를 pathVariable로 안받는이유 : 다른 페이지로 옮겨가는게 아닌 버튼을 눌러 클릭을 하는것이므로
    @PostMapping("/toss/payment/cancel")
    public ResponseEntity<PaymentCancelResponse> cancelPayment(@RequestBody PaymentCancelRequest paymentCancelRequest) {

        String tossPaymnetKey = tossPaymentService.getTossPaymentKey(paymentCancelRequest);
        String url = tossConfig.getBaseUrl() + "/v1/payments/"+tossPaymnetKey+"/cancel";

        TossPaymentCancelRequest tossPaymentCancelRequest =
                new TossPaymentCancelRequest(paymentCancelRequest.cancelReason());
        ResponseEntity<TossPaymentCancelResponse> res = tossRestTemplate.postForEntity(url,tossPaymentCancelRequest,
                TossPaymentCancelResponse.class);
        TossPaymentCancelResponse base = res.getBody();
        TossPaymentCancelResponse tossPaymentCancelResponse = new TossPaymentCancelResponse(base.orderId(),
                base.status());

        PaymentCancelResponse paymentCancelResponse = tossPaymentService.cancelPayment(tossPaymentCancelResponse);
        return ResponseEntity.ok(paymentCancelResponse);
    }

    @GetMapping(value = "/toss/confirm/{paymentId}")
    public ResponseEntity<PaymentResponse> getConfirmInfoById(@PathVariable Long paymentId) {
        PaymentResponse paymentResponse = tossPaymentService.getConfirmInfoById(paymentId);
        return ResponseEntity.ok(paymentResponse);
    }
}
