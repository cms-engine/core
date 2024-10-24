package com.ecommerce.engine.admin.dto.request;

import com.ecommerce.engine.admin.dto.common.MetaDescriptionDto;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record CategoryRequestDto(
        @EntityPresence(EntityType.CATEGORY) Long parentId,
        @EntityPresence(EntityType.IMAGE) UUID imageId,
        int sortOrder,
        boolean enabled,
        @NotEmpty Set<@Valid @NotNull MetaDescriptionDto> descriptions,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<@Valid @NotNull Attribute> attributes) {

    public record Attribute(
            @EntityPresence(EntityType.ATTRIBUTE) long id, boolean mandatory, boolean useInFilters, int sortOrder) {}
}
