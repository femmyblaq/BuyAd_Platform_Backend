package com.devinhy.Buyad_Platform.service.ScreenService;

import com.devinhy.Buyad_Platform.auth.dto.ScreenCreateRequest;
import com.devinhy.Buyad_Platform.auth.dto.ScreenResponse;
import com.devinhy.Buyad_Platform.model.Role;
import com.devinhy.Buyad_Platform.model.Screen;
import com.devinhy.Buyad_Platform.model.ScreenType;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final ScreenImageStorageService imageStorageService;

    @Value("${app.upload.screen-directory}")
    private String screenDirectory;
    @Transactional
    public ScreenResponse uploadScreen(ScreenCreateRequest request,
                               MultipartFile image, User user) throws IOException {
        if(user.getRole() != Role.SSP){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only SSP users can upload screen");
        }

        String imageUrl = imageStorageService.store(image);
        Screen screen = new Screen();
        screen.setScreenName(request.getScreenName().trim());
        screen.setDescription(request.getDescription().trim());
        screen.setPrice(request.getPrice());

        screen.setImageUrl(imageUrl);
        screen.setUser(user);

        screen.setLocation(request.getCountry().trim());
        screen.setCurrency(request.getCurrency().trim());
        screen.setScreenType(request.getScreenType());
        screen.setAvailable(true);

        Screen savedScreen = screenRepository.save(screen);
        return ScreenResponse.fromEntity(savedScreen);

    }

    @Transactional(readOnly = true)
    public List<ScreenResponse> getMyScreens(User user) {
        return screenRepository.findByUserId(user.getId()).stream().map(ScreenResponse::fromEntity).toList();
    }

    public ScreenResponse  getScreen(Long screenId, User user) {
        Screen screen = screenRepository.findByIdAndUserId(screenId, user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screen not found"));

        return ScreenResponse.fromEntity(screen);
    }

    public ScreenResponse updateScreen(Long screenId, ScreenCreateRequest request, MultipartFile image, User user) throws IOException {
        Screen screen = screenRepository.findByIdAndUserId(screenId, user.getId()).orElseThrow(() -> new RuntimeException("Screen not found"));


        screen.setDescription(request.getDescription().trim());
        screen.setScreenName(request.getScreenName().trim());
        screen.setCurrency(request.getCurrency().trim());
        screen.setPrice(request.getPrice());
        screen.setScreenType(request.getScreenType());
        screen.setLocation(request.getCountry().trim());

        if (image != null &&  !image.isEmpty()) {
            String imageFileName = imageStorageService.store(image);
            screen.setImageUrl(imageFileName);
        }
        Screen updatedScreen = screenRepository.save(screen);
        return ScreenResponse.fromEntity(updatedScreen);
    }
}
