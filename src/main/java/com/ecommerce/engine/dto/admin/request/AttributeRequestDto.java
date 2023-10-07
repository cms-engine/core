package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import com.ecommerce.engine.enums.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record AttributeRequestDto(
        @NotNull InputType inputType,
        boolean enabled,
        @NotNull @Size(min = 1) Set<@Valid NameDescriptionDto> descriptions) {}
