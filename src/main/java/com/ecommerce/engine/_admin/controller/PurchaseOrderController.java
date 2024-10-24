package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.PurchaseOrderGridDto;
import com.ecommerce.engine._admin.dto.request.PurchaseOrderRequestDto;
import com.ecommerce.engine._admin.dto.response.PurchaseOrderResponseDto;
import com.ecommerce.engine._admin.service.PurchaseOrderService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
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
@RequestMapping("/admin/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/search")
    public SearchResponse<PurchaseOrderGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return purchaseOrderService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public PurchaseOrderResponseDto get(@PathVariable UUID id) {
        return purchaseOrderService.get(id);
    }

    @PostMapping
    public PurchaseOrderResponseDto create(@Valid @RequestBody PurchaseOrderRequestDto requestDto) {
        return purchaseOrderService.save(requestDto);
    }

    @PutMapping("/{id}")
    public PurchaseOrderResponseDto update(
            @PathVariable UUID id, @Valid @RequestBody PurchaseOrderRequestDto requestDto) {
        return purchaseOrderService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        purchaseOrderService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<UUID> ids) {
        purchaseOrderService.deleteMany(ids);
    }
}
