package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public record CategoryRequestDto(
        @EntityPresence(EntityType.CATEGORY) Long parentId,
        @EntityPresence(EntityType.IMAGE) UUID imageId,
        int sortOrder,
        boolean enabled,
        @NotNull @Size(min = 1) Set<@Valid MetaDescriptionDto> descriptions) {}
