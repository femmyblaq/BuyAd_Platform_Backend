package com.devinhy.Buyad_Platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String token) {
        String verificationUrl = "http://localhost:8080/api/auth/verify" + token;
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Verify your ButAd account");
        message.setText("Welcome to BuyAd\n\n" +
                "Please verify your email by clicking the link  below: \n\n"+verificationUrl+"\n\n" + "This link expires in 24 hours.");

        mailSender.send(message);
    }
}
