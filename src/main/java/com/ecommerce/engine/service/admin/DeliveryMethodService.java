package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.grid.DeliveryMethodGridDto;
import com.ecommerce.engine.dto.admin.request.DeliveryMethodRequestDto;
import com.ecommerce.engine.dto.admin.response.DeliveryMethodResponseDto;
import com.ecommerce.engine.entity.DeliveryMethod;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.DeliveryMethodRepository;
import com.ecommerce.engine.service.ForeignKeysChecker;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryMethodService {

    private final DeliveryMethodRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

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
        checkExisting(id);

        DeliveryMethod deliveryMethod = new DeliveryMethod(requestDto);
        deliveryMethod.setId(id);
        DeliveryMethod saved = repository.save(deliveryMethod);
        return new DeliveryMethodResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(DeliveryMethod.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(DeliveryMethod.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private DeliveryMethod findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(DeliveryMethod.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(DeliveryMethod.TABLE_NAME, id);
        }
    }

    public SearchResponse<DeliveryMethodGridDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, DeliveryMethod.class, DeliveryMethodGridDto::new);
    }
}
