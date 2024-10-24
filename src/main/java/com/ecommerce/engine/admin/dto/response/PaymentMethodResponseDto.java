package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.PaymentMethod;
import java.util.Set;

public record PaymentMethodResponseDto(long id, boolean enabled, Set<NameDescriptionDto> descriptions) {

    public PaymentMethodResponseDto(PaymentMethod paymentMethod) {
        this(
                paymentMethod.getId(),
                paymentMethod.isEnabled(),
                NameDescriptionDto.createNameDescriptionDtoSet(paymentMethod.getDescriptions()));
    }
}
