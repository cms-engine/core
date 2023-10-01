package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
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
