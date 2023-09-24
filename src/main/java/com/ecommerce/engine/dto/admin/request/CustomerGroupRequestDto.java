package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CustomerGroupRequestDto(@NotNull @Size(min = 1) Set<@Valid NameDescriptionDto> descriptions) {}
