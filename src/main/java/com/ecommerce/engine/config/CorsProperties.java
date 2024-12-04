package com.ecommerce.engine.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Data
@ConfigurationProperties("app.cors")
public final class CorsProperties {
    private List<String> allowedOrigins = List.of(CorsConfiguration.ALL);
    private List<String> allowedMethods = List.of(CorsConfiguration.ALL);
    private List<String> allowedHeaders = List.of(CorsConfiguration.ALL);
}
