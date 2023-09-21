package com.ecommerce.engine.dto.request;

import com.ecommerce.engine.dto.common.PaymentMethodDescriptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record PaymentMethodRequestDto(
        boolean enabled, @NotNull @Size(min = 1) Set<@Valid PaymentMethodDescriptionDto> descriptions) {}
