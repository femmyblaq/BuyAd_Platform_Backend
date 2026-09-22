package com.devinhy.Buyad_Platform.controller;
import com.devinhy.Buyad_Platform.auth.dto.RegisterRequest;
import com.devinhy.Buyad_Platform.auth.dto.UserResponse;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.repository.ResponseMessage;
import com.devinhy.Buyad_Platform.service.AuthService.AuthService;
//import com.devinhy.Buyad_Platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse response = authService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
