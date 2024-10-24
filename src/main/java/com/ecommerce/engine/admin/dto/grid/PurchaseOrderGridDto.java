package com.ecommerce.engine.admin.dto.grid;

import com.ecommerce.engine.entity.PurchaseOrder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PurchaseOrderGridDto(
        UUID id,
        long statusId,
        String statusName,
        UUID customerId,
        String firstName,
        String lastName,
        String middleName,
        String phone,
        long paymentMethod,
        String paymentMethodName,
        long deliveryMethod,
        String deliveryMethodName,
        BigDecimal totalCost,
        Instant createdAt,
        Instant updatedAt) {

    public PurchaseOrderGridDto(PurchaseOrder purchaseOrder) {
        this(
                purchaseOrder.getId(),
                purchaseOrder.getStatus().getId(),
                purchaseOrder.getStatus().getLocaleName(),
                purchaseOrder.getCustomerId(),
                purchaseOrder.getFirstName(),
                purchaseOrder.getLastName(),
                purchaseOrder.getMiddleName(),
                purchaseOrder.getPhone(),
                purchaseOrder.getPaymentMethod().getId(),
                purchaseOrder.getPaymentMethod().getLocaleName(),
                purchaseOrder.getDeliveryMethod().getId(),
                purchaseOrder.getDeliveryMethod().getLocaleName(),
                purchaseOrder.getTotalCost(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getUpdatedAt());
    }
}
