package com.ecommerce.engine._admin.dto.grid;

import com.ecommerce.engine.entity.DeliveryMethod;

public record DeliveryMethodGridDto(long id, String name, boolean enabled) {

    public DeliveryMethodGridDto(DeliveryMethod deliveryMethod) {
        this(deliveryMethod.getId(), deliveryMethod.getLocaleName(), deliveryMethod.isEnabled());
    }
}
