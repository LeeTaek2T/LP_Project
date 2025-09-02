package com.example.lp.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Configuration
public class TossConfig {
    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.base-url}")
    private String baseUrl;

    @Bean
    public RestTemplate tossRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        String authValue = "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        ClientHttpRequestInterceptor authInterceptor = (request, body, excution) ->{
            request.getHeaders().add("Authorization", authValue);
            request.getHeaders().add("Content-Type", "application/json");
            return excution.execute(request,body);
        };

        restTemplate.setInterceptors((List.of(authInterceptor)));
        return restTemplate;
    }

    public String getBaseUrl(){
        return baseUrl;
    }
}
