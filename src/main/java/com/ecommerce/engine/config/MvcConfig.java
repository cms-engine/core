package com.ecommerce.engine.config;

import jakarta.annotation.Nonnull;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    public static final String IMAGE_FOLDER = "/images";
    private final EngineProperties engineProperties;

    @Override
    public void addResourceHandlers(@Nonnull ResourceHandlerRegistry registry) {
        String imageDir = engineProperties.getImageDir();
        if (imageDir == null) {
            imageDir = Path.of(Paths.get("").toAbsolutePath().toString(), IMAGE_FOLDER)
                    .toString();
        }

        registry.addResourceHandler(IMAGE_FOLDER + "/**").addResourceLocations("file:/" + imageDir);
    }
}
