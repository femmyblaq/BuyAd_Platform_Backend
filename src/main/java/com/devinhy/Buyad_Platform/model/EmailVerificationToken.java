package com.devinhy.Buyad_Platform.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String token;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;
    @Column(nullable = false)
    private LocalDateTime expiryDate;
    private LocalDateTime verifiedAt;
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
