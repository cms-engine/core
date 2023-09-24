package com.ecommerce.engine.controller;

import com.ecommerce.engine.dto.admin.grid.DeliveryMethodGridDto;
import com.ecommerce.engine.dto.admin.request.DeliveryMethodRequestDto;
import com.ecommerce.engine.dto.admin.response.DeliveryMethodResponseDto;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.service.DeliveryMethodService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-methods")
@RequiredArgsConstructor
public class DeliveryMethodController {

    private final DeliveryMethodService deliveryMethodService;

    @GetMapping("/search/{id}")
    public SearchResponse<DeliveryMethodGridDto> getGridPageCache(@PathVariable UUID id) {
        return deliveryMethodService.search(id, null);
    }

    @PostMapping("/search")
    public SearchResponse<DeliveryMethodGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return deliveryMethodService.search(null, searchRequest);
    }

    @GetMapping("/{id}")
    public DeliveryMethodResponseDto get(@PathVariable long id) {
        return deliveryMethodService.get(id);
    }

    @PostMapping
    public DeliveryMethodResponseDto create(@Valid @RequestBody DeliveryMethodRequestDto requestDto) {
        return deliveryMethodService.save(requestDto);
    }

    @PutMapping("/{id}")
    public DeliveryMethodResponseDto update(
            @PathVariable long id, @Valid @RequestBody DeliveryMethodRequestDto requestDto) {
        return deliveryMethodService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        deliveryMethodService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        deliveryMethodService.deleteMany(ids);
    }
}
