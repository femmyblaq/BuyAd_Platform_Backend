
package com.devinhy.Buyad_Platform.controller;

import com.devinhy.Buyad_Platform.auth.dto.ScreenCreateRequest;
import com.devinhy.Buyad_Platform.auth.dto.ScreenResponse;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.service.ScreenService.ScreenService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ssp/screen")
public class SspController {

    private final ScreenService screenService;

    public SspController(ScreenService screenService) {
        this.screenService = screenService;
    }

    // Create a screen with an uploaded image
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SSP')")
    public ResponseEntity<ScreenResponse> createScreen(
            @Valid @ModelAttribute ScreenCreateRequest request,
            @RequestParam("image") MultipartFile image,
            Authentication authentication) throws Exception {

        if (!(authentication.getPrincipal() instanceof User user)) {
            throw new IllegalStateException(
                    "The authenticated principal is not a User entity"
            );
        }

        ScreenResponse response =
                screenService.uploadScreen(request, image, user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // List screens belonging to the authenticated SSP
    @GetMapping
    @PreAuthorize("hasRole('SSP')")
    public ResponseEntity<List<ScreenResponse>> listScreens(
            Authentication authentication) {

        if (!(authentication.getPrincipal() instanceof User user)) {
            throw new IllegalStateException(
                    "The authenticated principal is not a User entity"
            );
        }

        return ResponseEntity.ok(
                screenService.getMyScreens(user)
        );
    }
}
