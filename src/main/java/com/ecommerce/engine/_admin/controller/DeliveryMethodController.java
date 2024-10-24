package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.DeliveryMethodGridDto;
import com.ecommerce.engine._admin.dto.request.DeliveryMethodRequestDto;
import com.ecommerce.engine._admin.dto.response.DeliveryMethodResponseDto;
import com.ecommerce.engine._admin.service.DeliveryMethodService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Set;
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
@RequestMapping("/admin/delivery-methods")
@RequiredArgsConstructor
public class DeliveryMethodController {

    private final DeliveryMethodService deliveryMethodService;

    @PostMapping("/search")
    public SearchResponse<DeliveryMethodGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return deliveryMethodService.search(searchRequest);
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
