package com.ecommerce.engine.util;

import java.util.Locale;
import java.util.Map;

public final class StoreSettings {

    public static boolean configured;
    public static String version;
    public static Locale defaultStoreLocale;
    public static int defaultStoreLocaleId;
    public static boolean allowAnonymousUsersToReviewProducts;
    public static boolean allowAnonymousUsersToReviewStore;
    public static boolean useCustomerGroups;
    public static Long defaultCustomerGroupId;
    public static String adminPasswordRegex;
    public static String storePasswordRegex;

    // Languages
    public static Map<Integer, Locale> storeLocales;

    private StoreSettings() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
