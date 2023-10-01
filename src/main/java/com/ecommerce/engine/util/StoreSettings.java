package com.ecommerce.engine.util;

import java.util.List;
import java.util.Locale;

public final class StoreSettings {

    public static String version;
    public static List<Locale> storeLocales;
    public static Locale defaultStoreLocale;
    public static boolean allowAnonymousUsersToReviewProducts;
    public static boolean allowAnonymousUsersToReviewStore;
    public static boolean useCustomerGroups;
    public static Long customerGroupIdByDefault;
    public static String adminPasswordRegex;
    public static String storePasswordRegex;

    private StoreSettings() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
