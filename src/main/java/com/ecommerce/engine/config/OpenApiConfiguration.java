package com.ecommerce.engine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    private static final String API_VERSION_1 = "v1";

    static {
        StringSchema localeSchema = new StringSchema();
        localeSchema.setExample("en-US");
        localeSchema.setExamples(List.of("en", "en-US"));
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

    @Bean
    public GroupedOpenApi storeOpenApi() {
        String[] paths = {"/store/**"};
        return GroupedOpenApi.builder().group("store").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi adminOpenApi() {
        String[] paths = {"/admin/**"};
        return GroupedOpenApi.builder().group("admin").pathsToMatch(paths).build();
    }

    @Bean
    public OpenApiCustomizer schemaRefCustomizer() {
        return openApi -> {
            // Get all schemas from the OpenAPI components
            var schemas = openApi.getComponents().getSchemas();

            if (schemas == null) {
                return;
            }

            var newSchemas = schemas.entrySet().stream()
                    .peek(entry -> replaceRefs(entry.getValue()))
                    .collect(Collectors.toMap(entry -> entry.getKey().replace("$", "."), Map.Entry::getValue));
            openApi.getComponents().setSchemas(newSchemas);
        };
    }

    private void replaceRefs(Schema<?> schema) {
        if (schema == null) {
            return;
        }

        String $ref = schema.get$ref();
        if ($ref != null) {
            schema.set$ref($ref.replace("$", "."));
        }

        replaceRefs(schema.getItems());

        var properties = schema.getProperties();
        if (properties != null) {
            properties.forEach((key, value) -> replaceRefs(value));
        }
    }
}
