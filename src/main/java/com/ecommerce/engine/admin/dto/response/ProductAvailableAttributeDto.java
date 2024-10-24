package com.ecommerce.engine.admin.dto.response;

import com.ecommerce.engine.entity.CategoryAttribute;

public record ProductAvailableAttributeDto(long id, String name, boolean mandatory) {
    public ProductAvailableAttributeDto(CategoryAttribute categoryAttribute) {
        this(
                categoryAttribute.getAttribute().getId(),
                categoryAttribute.getAttribute().getLocaleName(),
                categoryAttribute.isMandatory());
    }
}
