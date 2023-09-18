package com.ecommerce.engine.enums;

import com.ecommerce.engine.model.SearchField;
import com.ecommerce.engine.util.SearchFieldUtils;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchEntity {
    BRAND(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    CATEGORY(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    PRODUCT(Collections.emptyMap()),
    IMAGE(Collections.emptyMap());

    private final Map<String, SearchField> searchFields;
}
