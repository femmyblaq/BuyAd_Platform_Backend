package com.devinhy.Buyad_Platform.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${app.frontendUrl}")
    private String frontendUrl;
    private final JavaMailSender mailSender;

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    public void sendVerificationEmail(String email, String token) throws MessagingException {
        String verificationUrl = frontendUrl + "/verify-email?token=" + token;
//        SimpleMailMessage message = new SimpleMailMessage();

        String  htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="utf-8" />
                    <meta name="viewport" content="width=device-width,initial-scale=1" />
                    <title>Verification Email</title>
                    
                </head>
                <body>
                    <table role="presentation"
                            width="100%"
                            cellspacing="0"
                            cellpadding="0"
                            border="0"
                            style="background-color:#f3f6fb; padding:35px 12px;"
                            >
                            
                         <tr>
                            <td align="center">
                                <table role="presentation" 
                                width="100%"
                                cellspacing="0"
                                cellpadding="0"
                                border="0"
                                style="background-color:#ffffff; padding:35px 12px; 
                                max-width=500px; 
                                overflow: hidden; 
                                border: 1px solid #e5e7eb;"
                                >
                                    <tr align="center" style="background-color: #0f766e; padding:  30px 24px;">
                                    <h1 style="margin: 0; color: #ffffff; font-size: 30px; font-weight: bold; letter-spacing: 1px;">
                                    BuyAd
                                    </h1>
                                    
                                    <p style="margin: 8px 0 0;
                                    color: #ccfbf1
                                    font-size: 14px
                                    ">Digital Out-of-Home Advertising
                                    </p>
                                
                            </td>
                         </tr>
                         
                         Body - Mail
                         <tr>
                            td style="padding: 36px 30:px;>
                            <h2 style="margin: 0; color: #111827; color: 111827; font-size: 24px; line.14: 4">
                            
                            
                </body>
                </html>
                """.formatted(escapeHtml(verificationUrl));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        message.setSubject("Verify your ButAd account");
        message.setText("Welcome to BuyAd! " +
                "Please verify your email by clicking the link "+verificationUrl, htmlContent);

        mailSender.send(message);
    }
}
