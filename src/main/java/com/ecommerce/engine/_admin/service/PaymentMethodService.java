package com.ecommerce.engine._admin.service;

import com.ecommerce.engine._admin.dto.grid.PaymentMethodGridDto;
import com.ecommerce.engine._admin.dto.request.PaymentMethodRequestDto;
import com.ecommerce.engine._admin.dto.response.PaymentMethodResponseDto;
import com.ecommerce.engine.entity.PaymentMethod;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.PaymentMethodRepository;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public PaymentMethodResponseDto get(long id) {
        PaymentMethod paymentMethod = findById(id);
        return new PaymentMethodResponseDto(paymentMethod);
    }

    public PaymentMethodResponseDto save(PaymentMethodRequestDto requestDto) {
        PaymentMethod paymentMethod = new PaymentMethod(requestDto);
        PaymentMethod saved = repository.save(paymentMethod);
        return new PaymentMethodResponseDto(saved);
    }

    public PaymentMethodResponseDto update(long id, PaymentMethodRequestDto requestDto) {
        checkExisting(id);

        PaymentMethod paymentMethod = new PaymentMethod(requestDto);
        paymentMethod.setId(id);
        PaymentMethod saved = repository.save(paymentMethod);
        return new PaymentMethodResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(PaymentMethod.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(PaymentMethod.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private PaymentMethod findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(PaymentMethod.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(PaymentMethod.TABLE_NAME, id);
        }
    }

    public SearchResponse<PaymentMethodGridDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, PaymentMethod.class, PaymentMethodGridDto::new);
    }
}
