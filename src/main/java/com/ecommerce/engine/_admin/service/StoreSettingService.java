package com.ecommerce.engine._admin.service;

import com.ecommerce.engine._admin.dto.common.StoreSettingDto;
import com.ecommerce.engine.entity.StoreSetting;
import com.ecommerce.engine.repository.StoreSettingRepository;
import java.util.Optional;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

@Service
public class StoreSettingService {

    private final StoreSettingRepository storeSettingRepository;
    private final StoreSetting storeSetting;

    // TODO Prepare new settings via liquibase
    public StoreSettingService(StoreSettingRepository storeSettingRepository, BuildProperties buildProperties) {
        this.storeSettingRepository = storeSettingRepository;

        Optional<StoreSetting> settingOptional = storeSettingRepository.findById(1);

        StoreSetting storeSetting = settingOptional.orElseGet(StoreSetting::new);
        storeSetting.setVersion(buildProperties.getVersion());
        this.storeSetting = storeSettingRepository.save(storeSetting);

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
