package com.devinhy.Buyad_Platform.auth.dto;

import com.devinhy.Buyad_Platform.model.Screen;
import com.devinhy.Buyad_Platform.model.ScreenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ScreenResponse {
        private Long id;
        private String screenName;
        private BigDecimal price;
        private String description;
        private String location;
        private String imageUrl;
        private String country;
        private String currency;
        private Long userId;
        private Boolean available;
        private ScreenType screenType;
        private LocalDateTime createdAt;

        public static ScreenResponse fromEntity(Screen screen) {
            return new ScreenResponse(
                    screen.getId(),
                    screen.getScreenName(),
                    screen.getPrice(),
                    screen.getDescription(),
                    screen.getLocation(),
                    screen.getImageUrl(),
                    screen.getCountry(),
                    screen.getCurrency(),
                    screen.getUser().getId(),
                    screen.isAvailable(),
                    screen.getScreenType(),
                    screen.getCreatedAt()
            );
        }
    }
