package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.entity.OrderItem;
import com.ecommerce.engine.entity.PurchaseOrder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record PurchaseOrderResponseDto(
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
        String customerComment,
        String managerComment,
        Instant createdAt,
        Instant updatedAt,
        Set<OrderItem> items) {

    public PurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
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
                purchaseOrder.getCustomerComment(),
                purchaseOrder.getManagerComment(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getUpdatedAt(),
                purchaseOrder.getItems().stream().map(OrderItem::new).collect(Collectors.toSet()));
    }

    public record OrderItem(
            UUID id,
            long productId,
            String productTitle,
            String productImageSrc,
            BigDecimal price,
            BigDecimal quantity,
            BigDecimal cost) {
        public OrderItem(com.ecommerce.engine.entity.OrderItem orderItem) {
            this(
                    orderItem.getId(),
                    orderItem.getProduct().getId(),
                    orderItem.getProduct().getLocaleTitle(),
                    orderItem.getProduct().getImageSrc(),
                    orderItem.getPrice(),
                    orderItem.getQuantity(),
                    orderItem.getCost());
        }
    }
}
