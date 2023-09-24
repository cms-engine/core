package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.StoreSetting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Locale;

public record StoreSettingDto(
        @NotNull Locale adminLocale,
        @NotNull Locale storeLocale,
        boolean allowAnonymousUsersToReviewProducts,
        boolean allowAnonymousUsersToReviewStore,
        boolean useCustomerGroups,
        Long customerGroupIdByDefault,
        @NotBlank @Size(max = 255) String adminPasswordRegex,
        @NotBlank @Size(max = 255) String storePasswordRegex) {

    public StoreSettingDto(StoreSetting storeSetting) {
        this(
                storeSetting.getAdminLocale(),
                storeSetting.getStoreLocale(),
                storeSetting.isAllowAnonymousUsersToReviewProducts(),
                storeSetting.isAllowAnonymousUsersToReviewStore(),
                storeSetting.isUseCustomerGroups(),
                storeSetting.getCustomerGroupIdByDefault(),
                storeSetting.getAdminPasswordRegex(),
                storeSetting.getStorePasswordRegex());
    }
}
