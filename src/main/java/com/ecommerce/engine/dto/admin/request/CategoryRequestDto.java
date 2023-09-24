package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public record CategoryRequestDto(
        Long parentId,
        UUID imageId,
        int sortOrder,
        boolean enabled,
        @NotNull @Size(min = 1) Set<@Valid MetaDescriptionDto> descriptions) {}
