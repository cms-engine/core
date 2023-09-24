package com.ecommerce.engine.dto.request;

import com.ecommerce.engine.dto.common.MetaDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record PageRequestDto(
        int sortOrder,
        boolean bottom,
        boolean enabled,
        @NotNull @Size(min = 1) Set<@Valid MetaDescriptionDto> descriptions) {}
