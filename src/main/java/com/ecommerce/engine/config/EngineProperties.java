package com.ecommerce.engine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "engine")
public class EngineProperties {
    private String imageDir;
}
