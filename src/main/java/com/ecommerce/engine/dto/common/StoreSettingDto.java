package com.ecommerce.engine.dto.common;

import com.ecommerce.engine.entity.StoreSetting;
import java.util.Locale;

public record StoreSettingDto(Locale adminLocale, Locale storeLocale) {
    public StoreSettingDto(StoreSetting storeSetting) {
        this(storeSetting.getAdminLocale(), storeSetting.getStoreLocale());
    }
}
