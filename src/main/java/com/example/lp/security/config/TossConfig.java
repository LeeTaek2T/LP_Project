package com.example.lp.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class TossConfig {

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.base-url}")
    private String baseUrl;

    @Bean
    public RestTemplate tossRestTemplate() {
        // 타임아웃(선택)
        SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
        rf.setConnectTimeout(5_000);
        rf.setReadTimeout(10_000);

        RestTemplate restTemplate = new RestTemplate(rf);

        String basic = "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        ClientHttpRequestInterceptor authInterceptor = (request, body, execution) -> {
            // Authorization 없을 때만 설정
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                request.getHeaders().set(HttpHeaders.AUTHORIZATION, basic);
            }
            // 바디가 있는 메서드에만 Content-Type 설정 (이미 설정된 경우 유지)
            if (body != null && body.length > 0 && !request.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE)) {
                request.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
            }
            return execution.execute(request, body);
        };

        restTemplate.setInterceptors(List.of(authInterceptor));
        return restTemplate;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
