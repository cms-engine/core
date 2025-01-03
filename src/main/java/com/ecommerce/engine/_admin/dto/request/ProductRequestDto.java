package com.ecommerce.engine._admin.dto.request;

import com.ecommerce.engine._admin.dto.common.MetaDescriptionDto;
import com.ecommerce.engine.enumeration.LengthClass;
import com.ecommerce.engine.enumeration.WeightClass;
import com.ecommerce.engine.validation.EntityPresence;
import com.ecommerce.engine.validation.EntityType;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductRequestDto(
        @EntityPresence(EntityType.CATEGORY) Long categoryId,
        @EntityPresence(EntityType.IMAGE) UUID imageId,
        @Size(max = 64) String sku,
        @Size(max = 14) String ean,
        @Size(max = 64) String barcode,
        @EntityPresence(EntityType.BRAND) Long brandId,
        LengthClass lengthClass,
        @Digits(integer = 7, fraction = 8) BigDecimal length,
        @Digits(integer = 7, fraction = 8) BigDecimal width,
        @Digits(integer = 7, fraction = 8) BigDecimal height,
        WeightClass weightClass,
        @Digits(integer = 7, fraction = 8) BigDecimal weight,
        boolean enabled,
        @NotEmpty Set<@Valid @NotNull MetaDescriptionDto> descriptions,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<@Valid @NotNull AdditionalImage> additionalImages,
        @JsonSetter(nulls = Nulls.AS_EMPTY)
                Set<@Valid @NotNull @EntityPresence(EntityType.CATEGORY) Long> additionalCategories,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<@Valid @NotNull Attribute> attributes) {

    public record AdditionalImage(@EntityPresence(EntityType.IMAGE) UUID id, int sortOrder) {}

    public record Attribute(@EntityPresence(EntityType.ATTRIBUTE) long id, @NotBlank String attributeValue) {}
}
