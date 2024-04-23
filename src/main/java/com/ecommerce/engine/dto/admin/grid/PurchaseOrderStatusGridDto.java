package com.ecommerce.engine.dto.admin.grid;

import com.ecommerce.engine.entity.PurchaseOrderStatus;

public record PurchaseOrderStatusGridDto(long id, String name) {

    public PurchaseOrderStatusGridDto(PurchaseOrderStatus purchaseOrderStatus) {
        this(purchaseOrderStatus.getId(), purchaseOrderStatus.getLocaleName());
    }
}
