package com.devinhy.Buyad_Platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Entity
@Table(name = "screens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false)
    private String screenName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private boolean available;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScreenType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
