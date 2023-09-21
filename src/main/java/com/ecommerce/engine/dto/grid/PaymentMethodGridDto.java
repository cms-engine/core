package com.ecommerce.engine.dto.grid;

import com.ecommerce.engine.repository.entity.PaymentMethod;

public record PaymentMethodGridDto(long id, String name, boolean enabled) {

    public PaymentMethodGridDto(PaymentMethod paymentMethod) {
        this(paymentMethod.getId(), paymentMethod.getLocaleName(), paymentMethod.isEnabled());
    }
}
