package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.NameDescriptionDto;
import com.ecommerce.engine.repository.entity.PaymentMethod;
import java.util.Set;
import java.util.stream.Collectors;

public record PaymentMethodResponseDto(long id, boolean enabled, Set<NameDescriptionDto> descriptions) {

    public PaymentMethodResponseDto(PaymentMethod paymentMethod) {
        this(
                paymentMethod.getId(),
                paymentMethod.isEnabled(),
                paymentMethod.getDescriptions().stream()
                        .map(NameDescriptionDto::new)
                        .collect(Collectors.toSet()));
    }
}
