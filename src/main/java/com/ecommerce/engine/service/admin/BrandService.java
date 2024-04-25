package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.BrandRequestDto;
import com.ecommerce.engine.dto.admin.response.BrandResponseDto;
import com.ecommerce.engine.entity.Brand;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.BrandRepository;
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
public class BrandService implements EntityPresenceService<Long> {

    private final BrandRepository repository;
    private final SearchService searchService;
    private final ForeignKeysChecker foreignKeysChecker;

    public BrandResponseDto get(long id) {
        Brand brand = findById(id);
        return new BrandResponseDto(brand);
    }

    public BrandResponseDto save(BrandRequestDto requestDto) {
        Brand brand = new Brand(requestDto);
        Brand saved = repository.save(brand);
        return new BrandResponseDto(saved);
    }

    public BrandResponseDto update(long id, BrandRequestDto requestDto) {
        checkExisting(id);

        Brand brand = new Brand(requestDto);
        brand.setId(id);
        Brand saved = repository.save(brand);
        return new BrandResponseDto(saved);
    }

    public void delete(long id) {
        foreignKeysChecker.checkUsages(Brand.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Brand.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Brand findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Brand.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(Brand.TABLE_NAME, id);
        }
    }

    public SearchResponse<BrandResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, Brand.class, BrandResponseDto::new);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BRAND;
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
