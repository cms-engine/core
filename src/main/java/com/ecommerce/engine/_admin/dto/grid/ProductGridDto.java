package com.ecommerce.engine._admin.dto.grid;

import com.ecommerce.engine.entity.Product;
import com.ecommerce.engine.enumeration.LengthClass;
import com.ecommerce.engine.enumeration.WeightClass;
import java.math.BigDecimal;
import java.time.Instant;

public record ProductGridDto(
        long id,
        String title,
        Long categoryId,
        String categoryTitle,
        String imageSrc,
        String sku,
        String ean,
        String barcode,
        Long brandId,
        String brandName,
        LengthClass lengthClass,
        BigDecimal length,
        BigDecimal width,
        BigDecimal height,
        WeightClass weightClass,
        BigDecimal weight,
        Instant createdAt,
        Instant updatedAt,
        boolean enabled) {

    public ProductGridDto(Product product) {
        this(
                product.getId(),
                product.getLocaleTitle(),
                product.getCategoryId(),
                product.getCategoryLocaleTitle(),
                product.getImageSrc(),
                product.getSku(),
                product.getEan(),
                product.getBarcode(),
                product.getBrandId(),
                product.getBrandName(),
                product.getLengthClass(),
                product.getLength(),
                product.getWidth(),
                product.getHeight(),
                product.getWeightClass(),
                product.getWeight(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.isEnabled());
    }
}
