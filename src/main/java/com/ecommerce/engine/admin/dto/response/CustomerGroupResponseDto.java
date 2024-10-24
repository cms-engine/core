package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.CustomerGroup;
import java.util.Set;

public record CustomerGroupResponseDto(long id, Set<NameDescriptionDto> descriptions) {

    public CustomerGroupResponseDto(CustomerGroup customerGroup) {
        this(customerGroup.getId(), NameDescriptionDto.createNameDescriptionDtoSet(customerGroup.getDescriptions()));
    }
}
