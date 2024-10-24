package com.ecommerce.engine._admin.service;

import com.ecommerce.engine._admin.dto.grid.PurchaseOrderStatusGridDto;
import com.ecommerce.engine._admin.dto.request.PurchaseOrderStatusRequestDto;
import com.ecommerce.engine._admin.dto.response.PurchaseOrderStatusResponseDto;
import com.ecommerce.engine.entity.PurchaseOrderStatus;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.PurchaseOrderStatusRepository;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderStatusService {

    private final PurchaseOrderStatusRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public PurchaseOrderStatusResponseDto get(long id) {
        PurchaseOrderStatus purchaseOrderStatus = findById(id);
        return new PurchaseOrderStatusResponseDto(purchaseOrderStatus);
    }

    public PurchaseOrderStatusResponseDto save(PurchaseOrderStatusRequestDto requestDto) {
        PurchaseOrderStatus purchaseOrderStatus = new PurchaseOrderStatus(requestDto);
        PurchaseOrderStatus saved = repository.save(purchaseOrderStatus);
        return new PurchaseOrderStatusResponseDto(saved);
    }

    public PurchaseOrderStatusResponseDto update(long id, PurchaseOrderStatusRequestDto requestDto) {
        checkExisting(id);

        PurchaseOrderStatus purchaseOrderStatus = new PurchaseOrderStatus(requestDto);
        purchaseOrderStatus.setId(id);
        PurchaseOrderStatus saved = repository.save(purchaseOrderStatus);
        return new PurchaseOrderStatusResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(PurchaseOrderStatus.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(PurchaseOrderStatus.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private PurchaseOrderStatus findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(PurchaseOrderStatus.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(PurchaseOrderStatus.TABLE_NAME, id);
        }
    }

    public SearchResponse<PurchaseOrderStatusGridDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, PurchaseOrderStatus.class, PurchaseOrderStatusGridDto::new);
    }
}
