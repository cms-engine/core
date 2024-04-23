package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record PageRequestDto(
        int sortOrder, boolean bottom, boolean enabled, @NotEmpty Set<@Valid MetaDescriptionDto> descriptions) {}
