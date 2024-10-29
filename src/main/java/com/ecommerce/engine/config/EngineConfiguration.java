package com.ecommerce.engine.config;

import com.ecommerce.engine.validation.LocaleDeserializer;
import com.ecommerce.engine.validation.LocaleSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.LocaleResolver;

@EnableJpaAuditing
@Configuration
public class EngineConfiguration {

    public static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .addModule(new SimpleModule()
                        .addDeserializer(Locale.class, new LocaleDeserializer())
                        .addSerializer(Locale.class, new LocaleSerializer()))
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    public static final String APP_VERSION;

    static {
        Properties properties = new Properties();

        InputStream input = ValidationUtils.class.getResourceAsStream("/build.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        APP_VERSION = properties.getProperty("version");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return buildObjectMapper();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CustomAcceptHeaderLocaleResolver();
    }

    @Bean
    public HibernatePropertiesCustomizer jsonFormatMapperCustomizer(ObjectMapper objectMapper) {
        return properties ->
                properties.put(AvailableSettings.JSON_FORMAT_MAPPER, new JacksonJsonFormatMapper(objectMapper));
    }

    // TODO use real user id from security
    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(new Random().nextLong(100));
    }
}
