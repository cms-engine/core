package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.common.StoreSettingDto;
import com.ecommerce.engine.mapper.StoreSettingMapper;
import com.ecommerce.engine.repository.StoreSettingRepository;
import com.ecommerce.engine.repository.entity.StoreSetting;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoreSettingService {

    private final StoreSettingRepository storeSettingRepository;

    private final StoreSettingMapper storeSettingMapper;

    private final StoreSetting storeSetting;

    public StoreSettingService(StoreSettingRepository storeSettingRepository, StoreSettingMapper storeSettingMapper) {
        this.storeSettingRepository = storeSettingRepository;
        this.storeSettingMapper = storeSettingMapper;

        Optional<StoreSetting> settingOptional = storeSettingRepository.findById(1);

        storeSetting = settingOptional.orElseGet(StoreSetting::new);
        storeSetting.updateSettingsHolder();
    }

    public StoreSettingDto get() {
        return storeSettingMapper.toDto(storeSetting);
    }

    public StoreSettingDto update(StoreSettingDto storeSettingDto) {
        storeSettingMapper.mapUpdate(storeSetting, storeSettingDto);

        storeSettingRepository.save(storeSetting);
        storeSetting.updateSettingsHolder();

        return storeSettingMapper.toDto(storeSetting);
    }
}
