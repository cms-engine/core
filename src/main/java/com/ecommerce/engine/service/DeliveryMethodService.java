package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.request.DeliveryMethodRequestDto;
import com.ecommerce.engine.dto.response.DeliveryMethodResponseDto;
import com.ecommerce.engine.entity.DeliveryMethod;
import com.ecommerce.engine.enums.SearchEntity;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.model.SearchResponse;
import com.ecommerce.engine.repository.DeliveryMethodRepository;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryMethodService {

    private final DeliveryMethodRepository repository;
    private final SearchService<DeliveryMethod, DeliveryMethodResponseDto> searchService;

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
        return repository.findById(id).orElseThrow(() -> new NotFoundException("category", id));
    }

    public SearchResponse<DeliveryMethodResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(
                id, searchRequest, SearchEntity.BRAND, DeliveryMethod.class, DeliveryMethodResponseDto::new);
    }
}
