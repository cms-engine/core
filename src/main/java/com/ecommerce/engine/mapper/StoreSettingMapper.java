package com.ecommerce.engine.mapper;

import com.ecommerce.engine.dto.common.StoreSettingDto;
import com.ecommerce.engine.repository.entity.StoreSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StoreSettingMapper {
    @Mapping(target = "id", ignore = true)
    void mapUpdate(@MappingTarget StoreSetting storeSetting, StoreSettingDto storeSettingDto);

    StoreSettingDto toDto(StoreSetting storeSetting);
}
