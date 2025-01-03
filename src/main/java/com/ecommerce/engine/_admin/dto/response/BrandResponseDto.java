package com.ecommerce.engine._admin.dto.response;

import com.ecommerce.engine.entity.Brand;

public record BrandResponseDto(long id, String name) {
    public BrandResponseDto(Brand brand) {
        this(brand.getId(), brand.getName());
    }
}
