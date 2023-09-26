package com.ecommerce.engine.config.exception;

import java.time.Instant;

public record ErrorDto(long timestamp, String message, String code) {
    public ErrorDto(ApplicationException ex) {
        this(Instant.now().toEpochMilli(), ex.getMessage(), ex.getErrorCode().name());
    }
}
