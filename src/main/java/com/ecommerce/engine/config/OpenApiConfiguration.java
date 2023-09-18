package com.ecommerce.engine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import java.time.Instant;
import java.util.Currency;
import java.util.Locale;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    private static final String API_VERSION_1 = "v1";

    static {
        StringSchema localeSchema = new StringSchema();
        localeSchema.setExample("en_US");
        SpringDocUtils.getConfig().replaceWithSchema(Locale.class, localeSchema);

        StringSchema currencySchema = new StringSchema();
        currencySchema.setExample("USD");
        SpringDocUtils.getConfig().replaceWithSchema(Currency.class, currencySchema);

        NumberSchema instantSchema = new NumberSchema();
        instantSchema.setExample(1694977969853L);
        SpringDocUtils.getConfig().replaceWithSchema(Instant.class, instantSchema);
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title("API Documentation").version(API_VERSION_1));
    }
}
