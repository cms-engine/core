package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.request.BrandRequestDto;
import com.ecommerce.engine.dto.admin.response.BrandResponseDto;
import com.ecommerce.engine.entity.Brand;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.BrandRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import com.ecommerce.engine.validation.EntityType;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService implements EntityPresenceService<Long> {

    private final BrandRepository repository;
    private final SearchService<Brand, BrandResponseDto> searchService;
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
        findById(id);

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

    public SearchResponse<BrandResponseDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.BRAND, Brand.class, BrandResponseDto::new);
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
