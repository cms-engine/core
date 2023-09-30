package com.ecommerce.engine.entity;

import java.util.Collection;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;

@SuppressWarnings("SpellCheckingInspection")
public interface Localable {

    Locale getLocale();

    String getName();

    static String getLocaleName(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(localable -> LocaleContextHolder.getLocale().equals(localable.getLocale()))
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }
}
