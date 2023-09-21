package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.util.TranslationUtils;
import java.util.Collection;
import java.util.Locale;

public interface Localable {

    Locale getLocale();

    String getName();

    static String getLocaleTitle(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(Localable::isUserLocale)
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }

    default boolean isUserLocale() {
        return TranslationUtils.getUserLocale().equals(getLocale());
    }
}
