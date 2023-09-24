package com.ecommerce.engine.search;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchEntity {
    BRAND(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    CATEGORY(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    PRODUCT(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction())));

    private final Map<String, SearchField> searchFields;
}
