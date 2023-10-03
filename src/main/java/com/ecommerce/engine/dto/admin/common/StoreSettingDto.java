package com.ecommerce.engine.dto.admin.common;

import com.ecommerce.engine.entity.StoreSetting;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StoreSettingDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) String version,
        @NotNull @EntityPresence(EntityType.LANGUAGE) Integer defaultStoreLanguageId,
        boolean allowAnonymousUsersToReviewProducts,
        boolean allowAnonymousUsersToReviewStore,
        boolean useCustomerGroups,
        @EntityPresence(EntityType.CUSTOMER_GROUP) Long defaultCustomerGroupId,
        @NotBlank @Size(max = 255) String adminPasswordRegex,
        @NotBlank @Size(max = 255) String storePasswordRegex) {

    public StoreSettingDto(StoreSetting storeSetting) {
        this(
                storeSetting.getVersion(),
                storeSetting.getDefaultStoreLanguageId(),
                storeSetting.isAllowAnonymousUsersToReviewProducts(),
                storeSetting.isAllowAnonymousUsersToReviewStore(),
                storeSetting.isUseCustomerGroups(),
                storeSetting.getDefaultCustomerGroupId(),
                storeSetting.getAdminPasswordRegex(),
                storeSetting.getStorePasswordRegex());
    }
}
