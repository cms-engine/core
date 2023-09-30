package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.grid.DeliveryMethodGridDto;
import com.ecommerce.engine.dto.admin.request.DeliveryMethodRequestDto;
import com.ecommerce.engine.dto.admin.response.DeliveryMethodResponseDto;
import com.ecommerce.engine.entity.DeliveryMethod;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.DeliveryMethodRepository;
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
public class DeliveryMethodService {

    private final DeliveryMethodRepository repository;
    private final SearchService<DeliveryMethod, DeliveryMethodGridDto> searchService;

    public DeliveryMethodResponseDto get(long id) {
        DeliveryMethod deliveryMethod = findById(id);
        return new DeliveryMethodResponseDto(deliveryMethod);
    }

    public DeliveryMethodResponseDto save(DeliveryMethodRequestDto requestDto) {
        DeliveryMethod deliveryMethod = new DeliveryMethod(requestDto);
        DeliveryMethod saved = repository.save(deliveryMethod);
        return new DeliveryMethodResponseDto(saved);
    }

    public DeliveryMethodResponseDto update(long id, DeliveryMethodRequestDto requestDto) {
        findById(id);

        DeliveryMethod deliveryMethod = new DeliveryMethod(requestDto);
        deliveryMethod.setId(id);
        DeliveryMethod saved = repository.save(deliveryMethod);
        return new DeliveryMethodResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private DeliveryMethod findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(DeliveryMethod.TABLE_NAME, id));
    }

    public SearchResponse<DeliveryMethodGridDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(
                id, searchRequest, SearchEntity.DELIVERY_METHOD, DeliveryMethod.class, DeliveryMethodGridDto::new);
    }
}
