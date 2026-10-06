package com.devinhy.Buyad_Platform.service.ScreenService;

import com.devinhy.Buyad_Platform.model.Screen;
import com.devinhy.Buyad_Platform.model.ScreenType;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScreenService {
    private ScreenRepository screenRepository;

    @Value("${app.upload.screen-directory}")
    private String screenDirectory;

    public Screen uploadScreen(String screenName, String location, boolean available,
                               BigDecimal price, ScreenType screenType,
                               MultipartFile screenImage, User user) throws IOException {
        if(screenImage.isEmpty()) {
            throw new IllegalArgumentException("Screen image is required");
        }
        Path uploadPath = Paths.get(screenDirectory);
        Files.createDirectories(uploadPath);
        String originalFilename = screenImage.getOriginalFilename();
        String extension = "";
        if(originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(screenImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Screen screen = new Screen();
        screen.setScreenName(screenName);
        screen.setLocation(location);
        screen.setAvailable(available);
        screen.setType(screenType);
        screen.setImageUrl("/upload/screens/" + fileName);
        screen.setUser(user);

        return screenRepository.save(screen);
    }
}
