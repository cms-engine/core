package com.ecommerce.engine.entity;

import com.ecommerce.engine.util.TranslationUtils;
import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("SpellCheckingInspection")
public interface Localable {

    Locale getLocale();

    String getName();

    static String getLocaleName(Collection<? extends Localable> descriptions) {
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
