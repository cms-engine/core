package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
import com.ecommerce.engine.entity.CustomerGroup;
import java.util.Set;

public record CustomerGroupResponseDto(long id, Set<NameDescriptionDto> descriptions) {

    public CustomerGroupResponseDto(CustomerGroup customerGroup) {
        this(customerGroup.getId(), NameDescriptionDto.createNameDescriptionDtoSet(customerGroup.getDescriptions()));
    }
}
