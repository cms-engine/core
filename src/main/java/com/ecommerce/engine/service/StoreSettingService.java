package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.common.StoreSettingDto;
import com.ecommerce.engine.repository.StoreSettingRepository;
import com.ecommerce.engine.repository.entity.StoreSetting;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoreSettingService {

    private final StoreSettingRepository storeSettingRepository;
    private final StoreSetting storeSetting;

    public StoreSettingService(StoreSettingRepository storeSettingRepository) {
        this.storeSettingRepository = storeSettingRepository;

        Optional<StoreSetting> settingOptional = storeSettingRepository.findById(1);

        storeSetting = settingOptional.orElseGet(StoreSetting::new);
        storeSetting.updateSettingsHolder();
    }

    public StoreSettingDto get() {
        return new StoreSettingDto(storeSetting);
    }

    public StoreSettingDto update(StoreSettingDto storeSettingDto) {
        storeSetting.update(storeSettingDto);

        storeSettingRepository.save(storeSetting);
        storeSetting.updateSettingsHolder();

        return new StoreSettingDto(storeSetting);
    }
}
