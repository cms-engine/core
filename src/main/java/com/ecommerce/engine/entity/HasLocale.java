package com.ecommerce.engine.entity;

import com.ecommerce.engine.util.StoreSettings;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.springframework.context.i18n.LocaleContextHolder;

public interface HasLocale {

    Integer getLanguageId();

    String getName();

    static String getStoreDefaultLocaleName(Collection<? extends HasLocale> descriptions) {
        return descriptions.stream()
                .filter(hasLocale -> Objects.equals(StoreSettings.defaultStoreLocaleId, hasLocale.getLanguageId()))
                .findFirst()
                .map(HasLocale::getName)
                .orElse(null);
    }

    static String getRequestBasedLocaleName(Collection<? extends HasLocale> descriptions) {
        return descriptions.stream()
                .filter(hasLocale -> {
                    Locale contextLocale = LocaleContextHolder.getLocale();
                    Integer localeStoreId = StoreSettings.storeLocales.entrySet().stream()
                            .filter(entry -> entry.getValue().equals(contextLocale))
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null);
                    return hasLocale.getLanguageId().equals(localeStoreId);
                })
                .findFirst()
                .map(HasLocale::getName)
                .orElse(null);
    }
}
