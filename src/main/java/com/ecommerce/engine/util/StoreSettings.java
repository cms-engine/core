package com.ecommerce.engine.util;

public final class StoreSettings {

    public static String version;
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
