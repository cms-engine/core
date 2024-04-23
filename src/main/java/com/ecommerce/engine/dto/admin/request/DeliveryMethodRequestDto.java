package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

public record DeliveryMethodRequestDto(
        boolean enabled,
        @EntityPresence(EntityType.IMAGE) UUID imageId,
        @NotEmpty Set<@Valid NameDescriptionDto> descriptions) {}
