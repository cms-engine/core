package com.ecommerce.engine._admin.dto.request;

import com.ecommerce.engine._admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.enumeration.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record AttributeRequestDto(
        @NotNull InputType inputType,
        boolean enabled,
        @NotEmpty Set<@Valid @NotNull NameDescriptionDto> descriptions) {}
