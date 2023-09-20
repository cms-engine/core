package com.ecommerce.engine.dto.request;

import com.ecommerce.engine.dto.common.DescriptionDto;
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
        @NotNull @Size(min = 1) Set<@Valid DescriptionDto> descriptions) {}
