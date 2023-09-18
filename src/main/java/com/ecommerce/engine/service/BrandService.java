package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.request.BrandRequestDto;
import com.ecommerce.engine.dto.response.BrandResponseDto;
import com.ecommerce.engine.enums.SearchEntity;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.mapper.BrandMapper;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.model.SearchResponse;
import com.ecommerce.engine.repository.BrandRepository;
import com.ecommerce.engine.repository.entity.Brand;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository repository;
    private final BrandMapper mapper;
    private final SearchService<Brand, BrandResponseDto> searchService;

    public BrandResponseDto get(long id) {
        Brand brand = findById(id);
        return mapper.toDto(brand);
    }

    public BrandResponseDto save(BrandRequestDto requestDto) {
        Brand brand = mapper.toEntity(requestDto);
        Brand saved = repository.save(brand);
        return mapper.toDto(saved);
    }

    public BrandResponseDto update(long id, BrandRequestDto requestDto) {
        findById(id);

        Brand brand = mapper.toEntity(requestDto);
        brand.setId(id);
        Brand saved = repository.save(brand);
        return mapper.toDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private Brand findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("category", id));
    }

    public SearchResponse<BrandResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, SearchEntity.BRAND, Brand.class, mapper::toDto);
    }
}
