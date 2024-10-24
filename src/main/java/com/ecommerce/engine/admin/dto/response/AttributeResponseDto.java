package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
import com.ecommerce.engine.entity.Attribute;
import com.ecommerce.engine.enums.InputType;
import java.util.Set;

public record AttributeResponseDto(
        long id, InputType inputType, boolean enabled, Set<NameDescriptionDto> descriptions) {
    public AttributeResponseDto(Attribute attribute) {
        this(
                attribute.getId(),
                attribute.getInputType(),
                attribute.isEnabled(),
                NameDescriptionDto.createNameDescriptionDtoSet(attribute.getDescriptions()));
    }
}
