package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CustomerGroupRequestDto(@NotEmpty Set<@Valid NameDescriptionDto> descriptions) {}
