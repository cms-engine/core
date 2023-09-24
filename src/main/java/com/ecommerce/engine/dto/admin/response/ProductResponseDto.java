package com.ecommerce.engine.dto.admin.response;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import com.ecommerce.engine.entity.Product;
import com.ecommerce.engine.entity.ProductAdditionalImage;
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
        Instant created,
        Instant updated,
        boolean enabled,
        Set<MetaDescriptionDto> descriptions,
        Set<AdditionalImage> additionalImages) {

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
                product.getDescriptions().stream().map(MetaDescriptionDto::new).collect(Collectors.toSet()),
                product.getAdditionalImages().stream().map(AdditionalImage::new).collect(Collectors.toSet()));
    }

    public record AdditionalImage(UUID id, String src, int sortOrder) {
        public AdditionalImage(ProductAdditionalImage additionalImage) {
            this(additionalImage.getImageId(), additionalImage.getImageSrc(), additionalImage.getSortOrder());
        }
    }
}
