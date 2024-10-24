package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.DeliveryMethod;
import java.util.Set;

public record DeliveryMethodResponseDto(long id, boolean enabled, Set<NameDescriptionDto> descriptions) {

    public DeliveryMethodResponseDto(DeliveryMethod deliveryMethod) {
        this(
                deliveryMethod.getId(),
                deliveryMethod.isEnabled(),
                NameDescriptionDto.createNameDescriptionDtoSet(deliveryMethod.getDescriptions()));
    }
}
