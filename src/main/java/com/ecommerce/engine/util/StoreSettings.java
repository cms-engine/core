package com.ecommerce.engine.util;

import com.ecommerce.engine.entity.Language;
import java.util.List;

public final class StoreSettings {

    public static boolean configured;
    public static String version;
    public static boolean allowAnonymousUsersToReviewProducts;
    public static boolean allowAnonymousUsersToReviewStore;
    public static boolean useCustomerGroups;
    public static Long defaultCustomerGroupId;
    public static String adminPasswordRegex;
    public static String storePasswordRegex;

    // Languages
    public static List<Language> storeLanguages;
    public static Language defaultStoreLanguage;

    private StoreSettings() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
