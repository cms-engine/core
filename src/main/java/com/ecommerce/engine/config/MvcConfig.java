package com.ecommerce.engine.config;

import jakarta.annotation.Nonnull;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    public static final String IMAGES_FOLDER = "/images";
    public static final String IMAGES_PATTERN = IMAGES_FOLDER + "/**";
    private final EngineProperties engineProperties;

    @Override
    public void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
        String imageDir = engineProperties.getImageDir();
        if (imageDir == null) {
            imageDir = Path.of(Paths.get("").toAbsolutePath().toString(), IMAGES_FOLDER)
                    .toString();
        }

        registry.addResourceHandler(IMAGES_PATTERN)
                .addResourceLocations("file:/" + imageDir)
                .setCacheControl(
                        CacheControl.maxAge(Duration.ofDays(365)).cachePublic().immutable());
    }
}
