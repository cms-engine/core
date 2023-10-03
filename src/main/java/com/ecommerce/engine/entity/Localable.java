package com.ecommerce.engine.entity;

import com.ecommerce.engine.util.StoreSettings;
import java.util.Collection;
import org.springframework.context.i18n.LocaleContextHolder;

@SuppressWarnings("SpellCheckingInspection")
public interface Localable {

    Language getLanguage();

    String getName();

    static String getStoreDefaultLocaleName(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(localable -> StoreSettings.defaultStoreLocale.equals(
                        localable.getLanguage().getHreflang()))
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }

    static String getRequestBasedLocaleName(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(localable -> LocaleContextHolder.getLocale()
                        .equals(localable.getLanguage().getHreflang()))
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }
}
