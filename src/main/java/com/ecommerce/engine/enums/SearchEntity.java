package com.ecommerce.engine.enums;

import com.ecommerce.engine.model.SearchField;
import com.ecommerce.engine.repository.entity.Brand;
import com.ecommerce.engine.repository.entity.Category;
import com.ecommerce.engine.repository.entity.Image;
import com.ecommerce.engine.repository.entity.Product;
import com.ecommerce.engine.util.SearchFieldUtils;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchEntity {
    BRANDS(
            Brand.class,
            Map.of("id", new SearchField("id", SearchFieldUtils.NUMBER_FILTERS, SearchFieldUtils.toLongFunction()))),
    CATEGORIES(Category.class, Collections.emptyMap()),
    PRODUCTS(Product.class, Collections.emptyMap()),
    IMAGES(Image.class, Collections.emptyMap());

    private final Class<?> entityClass;
    private final Map<String, SearchField> searchFields;
}
