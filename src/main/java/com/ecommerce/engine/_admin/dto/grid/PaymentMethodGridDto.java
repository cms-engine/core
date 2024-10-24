package com.ecommerce.engine._admin.dto.grid;

import com.ecommerce.engine.entity.PaymentMethod;

public record PaymentMethodGridDto(long id, String name, boolean enabled) {

    public PaymentMethodGridDto(PaymentMethod paymentMethod) {
        this(paymentMethod.getId(), paymentMethod.getLocaleName(), paymentMethod.isEnabled());
    }
}
