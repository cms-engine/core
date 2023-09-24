package com.ecommerce.engine.search;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchEntity {
    BRAND(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    CATEGORY(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    PRODUCT(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    DELIVERY_METHOD(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    PAYMENT_METHOD(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    PAGE(Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    CUSTOMER_GROUP(
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction())));

    private final Map<String, SearchField> searchFields;
}
