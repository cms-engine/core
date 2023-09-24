package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.StoreSetting;
import java.util.Locale;

public record StoreSettingDto(
        Locale adminLocale,
        Locale storeLocale,
        boolean allowAnonymousUsersToReviewProducts,
        boolean allowAnonymousUsersToReviewStore,
        boolean useCustomerGroups,
        Long customerGroupIdByDefault) {
    public StoreSettingDto(StoreSetting storeSetting) {
        this(
                storeSetting.getAdminLocale(),
                storeSetting.getStoreLocale(),
                storeSetting.isAllowAnonymousUsersToReviewProducts(),
                storeSetting.isAllowAnonymousUsersToReviewStore(),
                storeSetting.isUseCustomerGroups(),
                storeSetting.getCustomerGroupIdByDefault()
        );
    }
}
