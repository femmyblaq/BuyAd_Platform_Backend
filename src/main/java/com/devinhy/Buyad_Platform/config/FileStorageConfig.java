package com.devinhy.Buyad_Platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {
    private final Path uploadDirectory;
    public FileStorageConfig (@Value("${app.upload.screen-directory}") String directory) {
        this.uploadDirectory = Paths.get("upload").toAbsolutePath().normalize();
    }
    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("/file:uploads/");
    }

}
