package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.request.PaymentMethodRequestDto;
import com.ecommerce.engine.dto.response.PaymentMethodResponseDto;
import com.ecommerce.engine.entity.PaymentMethod;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.PaymentMethodRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository repository;
    private final SearchService<PaymentMethod, PaymentMethodResponseDto> searchService;

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
        findById(id);

        PaymentMethod paymentMethod = new PaymentMethod(requestDto);
        paymentMethod.setId(id);
        PaymentMethod saved = repository.save(paymentMethod);
        return new PaymentMethodResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private PaymentMethod findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("category", id));
    }

    public SearchResponse<PaymentMethodResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(
                id, searchRequest, SearchEntity.BRAND, PaymentMethod.class, PaymentMethodResponseDto::new);
    }
}
