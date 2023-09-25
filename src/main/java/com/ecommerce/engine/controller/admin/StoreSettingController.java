package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.dto.admin.common.StoreSettingDto;
import com.ecommerce.engine.service.admin.StoreSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/settings")
@RequiredArgsConstructor
public class StoreSettingController {

    private final StoreSettingService storeSettingService;

    @GetMapping
    public StoreSettingDto get() {
        return storeSettingService.get();
    }

    @PutMapping
    public StoreSettingDto update(@RequestBody StoreSettingDto storeSettingDto) {
        return storeSettingService.update(storeSettingDto);
    }
}
