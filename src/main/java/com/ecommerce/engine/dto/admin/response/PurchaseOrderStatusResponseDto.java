package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import com.ecommerce.engine.entity.PurchaseOrderStatus;
import java.util.Set;

public record PurchaseOrderStatusResponseDto(long id, Set<NameDescriptionDto> descriptions) {

    public PurchaseOrderStatusResponseDto(PurchaseOrderStatus purchaseOrderStatus) {
        this(
                purchaseOrderStatus.getId(),
                NameDescriptionDto.createNameDescriptionDtoSet(purchaseOrderStatus.getDescriptions()));
    }
}
