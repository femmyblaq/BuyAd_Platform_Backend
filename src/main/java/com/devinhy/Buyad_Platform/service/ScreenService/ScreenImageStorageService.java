package com.devinhy.Buyad_Platform.service.ScreenService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;
@Service
public class ScreenImageStorageService {
    private final Path uploadDirectory;
    private static final  long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/jpg", "image/png", "image/webp");

    public ScreenImageStorageService(@Value("${app.upload.screen-directory}") String directory) {
        this.uploadDirectory = Paths.get(directory).toAbsolutePath().normalize();
    }

    public String store(MultipartFile image) throws IOException {
        if(image == null || image.isEmpty()) {
            throw new IllegalArgumentException("A screen image is required");
        }

        if(image.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("A screen image size cannot be greater than 5MB");
        }
        String contentType = image.getContentType();
        if(contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Only JPEG, JPG, PNG and WEBP images are supported.");
        }
        String extension = switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/jpg" -> "jpeg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            default -> throw new IllegalArgumentException("Unsupported content type: " + contentType);
        };
        try {
            Files.createDirectories(uploadDirectory);
            String fileName = UUID.randomUUID().toString() + "." + extension;
            Path destination = uploadDirectory.resolve(fileName).normalize();
            if (!destination.startsWith(uploadDirectory)) {
                throw new IllegalArgumentException("Invalid file destination");
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            return "/uploads/screen/" + fileName;
        }catch(IOException e) {
            throw new IOException("Failed to store screen image", e);
        }
    }
}
