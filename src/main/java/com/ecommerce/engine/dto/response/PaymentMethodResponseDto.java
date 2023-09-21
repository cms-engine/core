package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.PaymentMethodDescriptionDto;
import com.ecommerce.engine.repository.entity.PaymentMethod;
import java.util.Set;
import java.util.stream.Collectors;

public record PaymentMethodResponseDto(long id, boolean enabled, Set<PaymentMethodDescriptionDto> descriptions) {

    public PaymentMethodResponseDto(PaymentMethod paymentMethod) {
        this(
                paymentMethod.getId(),
                paymentMethod.isEnabled(),
                paymentMethod.getDescriptions().stream()
                        .map(PaymentMethodDescriptionDto::new)
                        .collect(Collectors.toSet()));
    }
}
