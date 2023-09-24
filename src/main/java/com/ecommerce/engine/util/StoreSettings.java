package com.ecommerce.engine.util;

import java.util.Locale;

public final class StoreSettings {

    public static Locale adminLocale;
    public static Locale storeLocale;
    public static boolean allowAnonymousUsersToReviewProducts;
    public static boolean allowAnonymousUsersToReviewStore;
    public static boolean useCustomerGroups;
    public static Long customerGroupIdByDefault;

    private StoreSettings() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
