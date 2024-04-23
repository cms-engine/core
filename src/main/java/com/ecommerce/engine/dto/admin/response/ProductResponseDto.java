package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import com.ecommerce.engine.entity.Product;
import com.ecommerce.engine.entity.ProductAdditionalImage;
import com.ecommerce.engine.entity.ProductAttribute;
import com.ecommerce.engine.enums.LengthClass;
import com.ecommerce.engine.enums.WeightClass;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductResponseDto(
        long id,
        Long categoryId,
        UUID imageId,
        String imageSrc,
        String sku,
        String ean,
        String barcode,
        Long brandId,
        LengthClass lengthClass,
        BigDecimal length,
        BigDecimal width,
        BigDecimal height,
        WeightClass weightClass,
        BigDecimal weight,
        Instant createdAt,
        Instant updatedAt,
        boolean enabled,
        Set<MetaDescriptionDto> descriptions,
        Set<AdditionalImage> additionalImages,
        Set<Long> additionalCategories,
        Set<Attribute> attributes) {

    public ProductResponseDto(Product product) {
        this(
                product.getId(),
                product.getCategoryId(),
                product.getImageId(),
                product.getImageSrc(),
                product.getSku(),
                product.getEan(),
                product.getBarcode(),
                product.getBrandId(),
                product.getLengthClass(),
                product.getLength(),
                product.getWidth(),
                product.getHeight(),
                product.getWeightClass(),
                product.getWeight(),
                product.getCreated(),
                product.getUpdated(),
                product.isEnabled(),
                MetaDescriptionDto.createMetaDescriptionDtoSet(product.getDescriptions()),
                product.getAdditionalImages().stream().map(AdditionalImage::new).collect(Collectors.toSet()),
                product.getAdditionalCategories().stream()
                        .map(category -> category.getCategory().getId())
                        .collect(Collectors.toSet()),
                product.getAttributes().stream().map(Attribute::new).collect(Collectors.toSet()));
    }

    public record AdditionalImage(UUID id, String src, int sortOrder) {
        public AdditionalImage(ProductAdditionalImage additionalImage) {
            this(additionalImage.getImageId(), additionalImage.getImageSrc(), additionalImage.getSortOrder());
        }
    }

    public record Attribute(long id, String value) {
        public Attribute(ProductAttribute attribute) {
            this(attribute.getAttribute().getId(), attribute.getValue());
        }
    }
}
