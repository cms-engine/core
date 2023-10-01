package com.ecommerce.engine.entity;

import com.ecommerce.engine.util.StoreSettings;
import java.util.Collection;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;

@SuppressWarnings("SpellCheckingInspection")
public interface Localable {

    Locale getLocale();

    String getName();

    static String getStoreDefaultLocaleName(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(localable -> StoreSettings.defaultStoreLocale.equals(localable.getLocale()))
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }

    static String getRequestBasedLocaleName(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(localable -> LocaleContextHolder.getLocale().equals(localable.getLocale()))
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }
}
