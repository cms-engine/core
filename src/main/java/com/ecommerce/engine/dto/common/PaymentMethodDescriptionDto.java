package com.ecommerce.engine.dto.common;

import com.ecommerce.engine.repository.entity.PaymentMethodDescription;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;

public record PaymentMethodDescriptionDto(@NotNull Locale locale, @NotBlank String name) {
    public PaymentMethodDescriptionDto(PaymentMethodDescription paymentMethodDescription) {
        this(paymentMethodDescription.getLocale(), paymentMethodDescription.getName());
    }
}
