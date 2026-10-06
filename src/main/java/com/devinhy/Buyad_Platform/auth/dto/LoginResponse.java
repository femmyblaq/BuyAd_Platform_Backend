package com.devinhy.Buyad_Platform.auth.dto;

public class LoginResponse {

    private final String token;
    private final UserResponse user;

    public LoginResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}
