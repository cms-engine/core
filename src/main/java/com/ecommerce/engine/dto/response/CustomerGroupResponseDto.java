package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.CustomerGroup;
import java.util.Set;
import java.util.stream.Collectors;

public record CustomerGroupResponseDto(long id, Set<NameDescriptionDto> descriptions) {

    public CustomerGroupResponseDto(CustomerGroup customerGroup) {
        this(
                customerGroup.getId(),
                customerGroup.getDescriptions().stream()
                        .map(NameDescriptionDto::new)
                        .collect(Collectors.toSet()));
    }
}
