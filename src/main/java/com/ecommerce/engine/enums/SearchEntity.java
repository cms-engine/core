package com.ecommerce.engine.enums;

import com.ecommerce.engine.model.SearchField;
import com.ecommerce.engine.util.SearchFieldUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum SearchEntity {
    BRANDS(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    CATEGORIES(Collections.emptyMap()),
    PRODUCTS(Collections.emptyMap()),
    IMAGES(Collections.emptyMap());

    private final Map<String, SearchField> searchFields;
}
