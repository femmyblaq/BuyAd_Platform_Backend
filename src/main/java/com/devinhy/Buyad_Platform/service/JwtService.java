package com.devinhy.Buyad_Platform.service;

import com.devinhy.Buyad_Platform.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long expirationTime;
    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expiration;
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token) {

        try {
            Claims claims = extractAllClaims(token);
            return true;
        }catch (Exception ex){
            return false;
        }

    }
    public String extractMail(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        }catch (Exception ex){
            return null;
        }

    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}
