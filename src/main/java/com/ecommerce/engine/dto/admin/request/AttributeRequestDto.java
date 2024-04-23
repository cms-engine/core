package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import com.ecommerce.engine.enums.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record AttributeRequestDto(
        @NotNull InputType inputType, boolean enabled, @NotEmpty Set<@Valid NameDescriptionDto> descriptions) {}
