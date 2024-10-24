package com.ecommerce.engine.admin.dto.request;

import com.ecommerce.engine.admin.dto.common.MetaDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record PageRequestDto(
        int sortOrder,
        boolean bottom,
        boolean enabled,
        @NotEmpty Set<@Valid @NotNull MetaDescriptionDto> descriptions) {}
