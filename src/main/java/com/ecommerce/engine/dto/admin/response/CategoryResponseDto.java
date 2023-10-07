package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import com.ecommerce.engine.entity.Category;
import com.ecommerce.engine.entity.CategoryAttribute;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record CategoryResponseDto(
        long id,
        Long parentId,
        UUID imageId,
        String imageSrc,
        int sortOrder,
        Instant created,
        Instant updated,
        boolean enabled,
        Set<MetaDescriptionDto> descriptions,
        Set<Attribute> attributes) {
    public CategoryResponseDto(Category category) {
        this(
                category.getId(),
                category.getParentId(),
                category.getImageId(),
                category.getImageSrc(),
                category.getSortOrder(),
                category.getCreated(),
                category.getUpdated(),
                category.isEnabled(),
                MetaDescriptionDto.createMetaDescriptionDtoSet(category.getDescriptions()),
                category.getAttributes().stream().map(Attribute::new).collect(Collectors.toSet()));
    }

    public record Attribute(long id, boolean mandatory, boolean useInFilters, int sortOrder) {
        public Attribute(CategoryAttribute categoryAttribute) {
            this(
                    categoryAttribute.getAttribute().getId(),
                    categoryAttribute.isMandatory(),
                    categoryAttribute.isUseInFilters(),
                    categoryAttribute.getSortOrder());
        }
    }
}
