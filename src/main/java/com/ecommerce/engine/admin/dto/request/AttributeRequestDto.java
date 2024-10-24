package com.ecommerce.engine.admin.dto.request;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.enums.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record AttributeRequestDto(
        @NotNull InputType inputType,
        boolean enabled,
        @NotEmpty Set<@Valid @NotNull NameDescriptionDto> descriptions) {}
