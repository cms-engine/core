package com.ecommerce.engine._admin.dto.response;

import com.ecommerce.engine._admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.CustomerGroup;
import java.util.Set;

public record CustomerGroupResponseDto(long id, Set<NameDescriptionDto> descriptions) {

    public CustomerGroupResponseDto(CustomerGroup customerGroup) {
        this(customerGroup.getId(), NameDescriptionDto.createNameDescriptionDtoSet(customerGroup.getDescriptions()));
    }
}
