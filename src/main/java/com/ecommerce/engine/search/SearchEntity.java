package com.ecommerce.engine.search;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchEntity {
    BRAND(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    CATEGORY(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    PRODUCT(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    DELIVERY_METHOD(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    PAYMENT_METHOD(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    PAGE(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    CUSTOMER_GROUP(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong))),
    CUSTOMER(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, Long::parseLong)));

    private final Map<String, SearchField> searchFields;
}
