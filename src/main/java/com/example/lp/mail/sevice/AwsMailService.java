package com.example.lp.mail.sevice;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.example.lp.util.MailUtil;
import org.springframework.stereotype.Service;

@Service
public class AwsMailService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public AwsMailService(AmazonSimpleEmailService amazonSimpleEmailService){
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    public void send(String body_html) {
        try {
            SendRawEmailRequest sendRawEmailRequest = MailUtil.getSendRawEmailRequest("Kafka 적용한 aws ses 이메일 보내기", "Test Message", "dlxor2306@gmail.com", body_html, null);
            amazonSimpleEmailService.sendRawEmail(sendRawEmailRequest);
        } catch (Exception e) {
        }

    }
}
