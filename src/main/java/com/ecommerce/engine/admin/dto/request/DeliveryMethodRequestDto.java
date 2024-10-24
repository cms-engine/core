package com.ecommerce.engine.admin.dto.request;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record DeliveryMethodRequestDto(
        boolean enabled,
        @EntityPresence(EntityType.IMAGE) UUID imageId,
        @NotEmpty Set<@Valid @NotNull NameDescriptionDto> descriptions) {}
