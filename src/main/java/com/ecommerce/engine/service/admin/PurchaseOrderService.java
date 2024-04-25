package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.PurchaseOrderRequestDto;
import com.ecommerce.engine.dto.admin.response.PurchaseOrderResponseDto;
import com.ecommerce.engine.entity.PurchaseOrder;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.PurchaseOrderRepository;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public PurchaseOrderResponseDto get(UUID id) {
        PurchaseOrder purchaseOrder = findById(id);
        return new PurchaseOrderResponseDto(purchaseOrder);
    }

    public PurchaseOrderResponseDto update(UUID id, PurchaseOrderRequestDto requestDto) {
        checkExisting(id);

        PurchaseOrder purchaseOrder = new PurchaseOrder(requestDto);

        purchaseOrder.setId(id);
        PurchaseOrder saved = repository.save(purchaseOrder);
        return new PurchaseOrderResponseDto(saved);
    }

    public void delete(UUID id) {
        foreignKeysChecker.checkUsages(PurchaseOrder.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<UUID> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(PurchaseOrder.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private PurchaseOrder findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(PurchaseOrder.TABLE_NAME, id));
    }

    private void checkExisting(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(PurchaseOrder.TABLE_NAME, id);
        }
    }

    public SearchResponse<PurchaseOrderResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, PurchaseOrder.class, PurchaseOrderResponseDto::new);
    }
}
