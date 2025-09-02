package com.example.lp.order.controller;

import com.example.lp.order.dto.request.ConfirmRequest;
import com.example.lp.order.dto.request.OrderPreRequest;
import com.example.lp.order.dto.response.OrderPreResponse;
import com.example.lp.order.service.TossPaymentService;
import com.example.lp.security.config.TossConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    @PostMapping("/toss/orderPre")
    public ResponseEntity<OrderPreResponse> savePreOrder(Authentication auth,
                                                         @RequestBody OrderPreRequest orderPreRequest){
        OrderPreResponse orderPreResponse = tossPaymentService.savePreOrder(orderPreRequest);
        return ResponseEntity.ok(orderPreResponse);
    }

    @PostMapping("/toss/confirm")
    public ResponseEntity<Map<String, Object>> confirm(@RequestBody ConfirmRequest confirmRequest){
        String url = tossConfig.getBaseUrl() + "/v1/payments/confirm";
        ResponseEntity<Map> res = tossRestTemplate.postForEntity(url, confirmRequest, Map.class);
        return ResponseEntity.status(res.getStatusCode()).body(res.getBody());


    }

}
