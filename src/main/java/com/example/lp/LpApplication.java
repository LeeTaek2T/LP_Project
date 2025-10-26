package com.example.lp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.lp.mail.sevice.AwsMailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class LpApplication {

    public static void main(String[] args) {
        SpringApplication.run(LpApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run(AwsMailService awsMailService) {
//        return args -> {
//            System.out.println("애플리케이션 시작... 메일 발송을 실행합니다.");
//            awsMailService.send();
//            System.out.println("메일 발송 메소드 호출 완료.");
//        };
//    }

}
