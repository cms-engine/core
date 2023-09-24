package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.DeliveryMethod;
import java.util.Set;
import java.util.stream.Collectors;

public record DeliveryMethodResponseDto(long id, boolean enabled, Set<NameDescriptionDto> descriptions) {

    public DeliveryMethodResponseDto(DeliveryMethod deliveryMethod) {
        this(
                deliveryMethod.getId(),
                deliveryMethod.isEnabled(),
                deliveryMethod.getDescriptions().stream()
                        .map(NameDescriptionDto::new)
                        .collect(Collectors.toSet()));
    }
}
