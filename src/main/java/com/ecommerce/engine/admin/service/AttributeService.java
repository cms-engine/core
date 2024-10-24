package com.ecommerce.engine.admin.service;

import com.ecommerce.engine.admin.dto.grid.AttributeGridDto;
import com.ecommerce.engine.admin.dto.request.AttributeRequestDto;
import com.ecommerce.engine.admin.dto.response.AttributeResponseDto;
import com.ecommerce.engine.entity.Attribute;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.AttributeRepository;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import com.ecommerce.engine.validation.EntityType;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttributeService implements EntityPresenceService<Long> {

    private final AttributeRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public AttributeResponseDto get(long id) {
        Attribute attribute = findById(id);
        return new AttributeResponseDto(attribute);
    }

    public AttributeResponseDto save(AttributeRequestDto requestDto) {
        Attribute attribute = new Attribute(requestDto);
        Attribute saved = repository.save(attribute);
        return new AttributeResponseDto(saved);
    }

    public AttributeResponseDto update(long id, AttributeRequestDto requestDto) {
        checkExisting(id);

        Attribute attribute = new Attribute(requestDto);
        attribute.setId(id);
        Attribute saved = repository.save(attribute);
        return new AttributeResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(Attribute.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Attribute.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Attribute findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Attribute.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(Attribute.TABLE_NAME, id);
        }
    }

    public SearchResponse<AttributeGridDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, Attribute.class, AttributeGridDto::new);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ATTRIBUTE;
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
