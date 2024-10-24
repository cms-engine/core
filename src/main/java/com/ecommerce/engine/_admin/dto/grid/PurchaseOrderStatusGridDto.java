package com.ecommerce.engine._admin.dto.grid;

import com.ecommerce.engine.entity.PurchaseOrderStatus;

public record PurchaseOrderStatusGridDto(long id, String name) {

    public PurchaseOrderStatusGridDto(PurchaseOrderStatus purchaseOrderStatus) {
        this(purchaseOrderStatus.getId(), purchaseOrderStatus.getLocaleName());
    }
}
