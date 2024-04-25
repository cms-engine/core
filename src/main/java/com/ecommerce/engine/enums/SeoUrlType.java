package com.ecommerce.engine.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeoUrlType {
    CATEGORY("seoUrlType.category"),
    PRODUCT("seoUrlType.product"),
    PAGE("seoUrlType.page");

    private final String translationKey;
}
