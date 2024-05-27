package com.example.alumni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${file.storage-dir}")
    private String fileStorageLocation;

    @Value("${file.client-dir}")
    private String clientLocation;

    @Value("${file.storage-dir1}")
    private String fileStorageLocation1;

    @Value("${file.client-dir1}")
    private String clientLocation1;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientLocation)
                .addResourceLocations("file:" + fileStorageLocation);

        registry.addResourceHandler(clientLocation1)
                .addResourceLocations("file:" + fileStorageLocation1);
    }
}

