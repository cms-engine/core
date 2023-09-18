package com.ecommerce.engine.mapper;

import com.ecommerce.engine.dto.request.BrandRequestDto;
import com.ecommerce.engine.dto.response.BrandResponseDto;
import com.ecommerce.engine.repository.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BrandMapper {

    BrandResponseDto toDto(Brand brand);

    @Mapping(target = "id", ignore = true)
    Brand toEntity(BrandRequestDto brandRequestDto);
}
