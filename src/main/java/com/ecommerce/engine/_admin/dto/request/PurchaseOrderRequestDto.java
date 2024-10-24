package com.ecommerce.engine._admin.dto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record PurchaseOrderRequestDto(
        long statusId,
        UUID customerId,
        String firstName,
        String lastName,
        String middleName,
        String phone,
        long paymentMethodId,
        long deliveryMethodId,
        String customerComment,
        String managerComment,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<@Valid @NotNull OrderItem> items) {

    public record OrderItem(
            long productId,
            @NotNull @Digits(integer = 12, fraction = 3) BigDecimal price,
            @NotNull @Digits(integer = 12, fraction = 3) @Positive BigDecimal quantity,
            @NotNull @Digits(integer = 12, fraction = 3) BigDecimal cost) {}
}
