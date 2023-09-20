package com.ecommerce.engine.dto.response;

import com.ecommerce.engine.enums.LengthClass;
import com.ecommerce.engine.enums.WeightClass;
import com.ecommerce.engine.repository.entity.Product;
import java.math.BigDecimal;
import java.time.Instant;

public record ProductGridResponseDto(
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
        Instant created,
        Instant updated,
        boolean enabled) {

    public ProductGridResponseDto(Product product) {
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
                product.getCreated(),
                product.getUpdated(),
                product.isEnabled());
    }
}
