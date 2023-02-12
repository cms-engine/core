package com.ecommerce.engine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    private static final String API_VERSION_1 = "v1";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Engine API Documentation")
                        .version(API_VERSION_1)
                        .description("/api/v1"));
    }
}
