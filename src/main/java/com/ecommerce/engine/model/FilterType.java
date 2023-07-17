package com.ecommerce.engine.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public enum FilterType {
    EQUAL(String.class, Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    NOT_EQUAL(String.class, Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    IN(String.class, Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    NOT_IN(String.class, Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    LIKE(String.class),
    NOT_LIKE(String.class),
    GRATER_THAN(Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    LESS_THAN(Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    GRATER_THAN_OR_EQUAL(Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class),
    LESS_THAN_OR_EQUAL(Integer.class, Long.class, Instant.class, LocalDate.class, BigDecimal.class);

    private final List<Class<?>> allowedClasses;

    FilterType(Class<?>... allowedClasses) {
        this.allowedClasses = List.of(allowedClasses);
    }

    public List<Class<?>> getAllowedClasses() {
        return Collections.unmodifiableList(allowedClasses);
    }
}
