package com.ecommerce.engine.entity;

import com.ecommerce.engine.util.StoreSettings;
import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("SpellCheckingInspection")
public interface Localable {

    Locale getLocale();

    String getName();

    static String getAdminLocaleName(Collection<? extends Localable> descriptions) {
        return descriptions.stream()
                .filter(localable -> StoreSettings.defaultStoreLocale.equals(localable.getLocale()))
                .findFirst()
                .map(Localable::getName)
                .orElse(null);
    }
}
