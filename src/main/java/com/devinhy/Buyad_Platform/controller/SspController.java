package com.devinhy.Buyad_Platform.controller;

import com.devinhy.Buyad_Platform.model.Screen;
import com.devinhy.Buyad_Platform.model.ScreenType;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.service.ScreenService.ScreenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ssp")
public class SspController {
    private final ScreenService screenService;
    public SspController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping("/screen")
    public ResponseEntity<Screen> uploadScreen(@RequestParam String screenName,
                                               @RequestParam String location,
                                               @RequestParam boolean available,
                                               @RequestParam BigDecimal price,
                                               @RequestParam ScreenType screenType,
                                               @RequestParam MultipartFile image,
                                               Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();

        Screen screen = screenService.uploadScreen(screenName, location, available, price, screenType, image, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(screen);
    }
}
