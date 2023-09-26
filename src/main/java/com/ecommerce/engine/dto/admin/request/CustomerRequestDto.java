package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.constraints.Size;

public record CustomerRequestDto(
        @EntityPresence(EntityType.CUSTOMER_GROUP) Long customerGroupId,
        @Size(max = 255) String firstName,
        @Size(max = 255) String lastName,
        @Size(max = 255) String middleName,
        @Size(max = 255) String phone,
        boolean newsletter,
        boolean enabled) {}
