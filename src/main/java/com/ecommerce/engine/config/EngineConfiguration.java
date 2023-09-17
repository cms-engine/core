package com.ecommerce.engine.config;

import com.ecommerce.engine.config.exception_handler.ApplicationExceptionResolver;
import com.ecommerce.engine.config.exception_handler.CustomErrorAttributes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineConfiguration {

    public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .enable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
            .enable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    @Bean
    public ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    @Bean
    public ApplicationExceptionResolver customExceptionResolver() {
        return new ApplicationExceptionResolver();
    }

    @Bean
    public CustomErrorAttributes customErrorAttributes() {
        return new CustomErrorAttributes();
    }
}
