package com.ecommerce.engine.controller;

import com.ecommerce.engine.dto.common.StoreSettingDto;
import com.ecommerce.engine.service.StoreSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
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
