package com.ecommerce.engine._admin.dto.request;

import com.ecommerce.engine._admin.dto.common.NameDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record CustomerGroupRequestDto(@NotEmpty Set<@Valid @NotNull NameDescriptionDto> descriptions) {}
