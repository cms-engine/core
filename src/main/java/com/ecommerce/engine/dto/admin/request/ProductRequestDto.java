package com.ecommerce.engine.dto.admin.request;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import com.ecommerce.engine.enums.LengthClass;
import com.ecommerce.engine.enums.WeightClass;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductRequestDto(
        Long categoryId,
        UUID imageId,
        @Size(max = 64) String sku,
        @Size(max = 14) String ean,
        @Size(max = 64) String barcode,
        Long brandId,
        LengthClass lengthClass,
        @Digits(integer = 7, fraction = 8) BigDecimal length,
        @Digits(integer = 7, fraction = 8) BigDecimal width,
        @Digits(integer = 7, fraction = 8) BigDecimal height,
        WeightClass weightClass,
        @Digits(integer = 7, fraction = 8) BigDecimal weight,
        boolean enabled,
        @NotNull @Size(min = 1) Set<@Valid MetaDescriptionDto> descriptions,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Set<AdditionalImage> additionalImages) {

    public record AdditionalImage(UUID id, int sortOrder) {}
}
