package com.devinhy.Buyad_Platform.auth.dto;


import com.devinhy.Buyad_Platform.model.ScreenType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScreenCreateRequest {
    @NotBlank(message = "Screen name is required")
    @Size(max = 150)
    private String screenName;
    @Size(max = 1000)
    private String description;
    @NotBlank(message = "Country is required")
    @Size(max = 1000)
    private String country;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    private String city;
    @NotNull(message = "Screen type is required")
    private ScreenType screenType;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    @NotNull(message = "Currency is required.")
    private String currency;
}
