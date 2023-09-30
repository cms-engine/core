package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.grid.PaymentMethodGridDto;
import com.ecommerce.engine.dto.admin.request.PaymentMethodRequestDto;
import com.ecommerce.engine.dto.admin.response.PaymentMethodResponseDto;
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
    private final SearchService<PaymentMethod, PaymentMethodGridDto> searchService;

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
        return repository.findById(id).orElseThrow(() -> new NotFoundException(PaymentMethod.TABLE_NAME, id));
    }

    public SearchResponse<PaymentMethodGridDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(
                id, searchRequest, SearchEntity.PAYMENT_METHOD, PaymentMethod.class, PaymentMethodGridDto::new);
    }
}
