package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.StoreSetting;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StoreSettingDto(
        boolean allowAnonymousUsersToReviewProducts,
        boolean allowAnonymousUsersToReviewStore,
        boolean useCustomerGroups,
        @EntityPresence(EntityType.CUSTOMER_GROUP) Long customerGroupIdByDefault,
        @NotBlank @Size(max = 255) String adminPasswordRegex,
        @NotBlank @Size(max = 255) String storePasswordRegex) {

    public StoreSettingDto(StoreSetting storeSetting) {
        this(
                storeSetting.isAllowAnonymousUsersToReviewProducts(),
                storeSetting.isAllowAnonymousUsersToReviewStore(),
                storeSetting.isUseCustomerGroups(),
                storeSetting.getCustomerGroupIdByDefault(),
                storeSetting.getAdminPasswordRegex(),
                storeSetting.getStorePasswordRegex());
    }
}