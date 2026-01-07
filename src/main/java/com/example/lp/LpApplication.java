package com.example.lp;

import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.lp.mail.sevice.AwsMailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LpApplication {

    public static void main(String[] args) {
//        String rawPassword = "b1"; // 암호화할 비밀번호
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        String encodedPassword = encoder.encode(rawPassword);
//
//        System.out.println("Encoded password: " + encodedPassword);
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
