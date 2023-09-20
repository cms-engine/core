package com.ecommerce.engine.model;

import com.ecommerce.engine.enums.FilterType;
import java.util.Collection;
import java.util.function.Function;

public record SearchField(
        String path, Collection<FilterType> allowedFilterTypes, Function<String, Object> convertFunction) {
    public SearchField(String path, Collection<FilterType> allowedFilterTypes) {
        this(path, allowedFilterTypes, s -> s);
    }
}
