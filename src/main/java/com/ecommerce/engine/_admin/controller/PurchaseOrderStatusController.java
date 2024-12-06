package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.PurchaseOrderStatusGridDto;
import com.ecommerce.engine._admin.dto.request.PurchaseOrderStatusRequestDto;
import com.ecommerce.engine._admin.dto.response.PurchaseOrderStatusResponseDto;
import com.ecommerce.engine._admin.service.PurchaseOrderStatusService;
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
@RequestMapping("/admin/purchase-order-statuses")
@RequiredArgsConstructor
public class PurchaseOrderStatusController {

    private final PurchaseOrderStatusService purchaseOrderStatusService;

    @PostMapping("/search")
    public SearchResponse<PurchaseOrderStatusGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return purchaseOrderStatusService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public PurchaseOrderStatusResponseDto get(@PathVariable long id) {
        return purchaseOrderStatusService.get(id);
    }

    @PostMapping
    public PurchaseOrderStatusResponseDto create(@Valid @RequestBody PurchaseOrderStatusRequestDto requestDto) {
        return purchaseOrderStatusService.save(requestDto);
    }

    @PutMapping("/{id}")
    public PurchaseOrderStatusResponseDto update(
            @PathVariable long id, @Valid @RequestBody PurchaseOrderStatusRequestDto requestDto) {
        return purchaseOrderStatusService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        purchaseOrderStatusService.delete(id);
    }

    @PostMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        purchaseOrderStatusService.deleteMany(ids);
    }
}
