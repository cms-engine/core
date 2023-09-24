package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.grid.CategoryGridDto;
import com.ecommerce.engine.dto.request.CategoryRequestDto;
import com.ecommerce.engine.dto.response.CategoryResponseDto;
import com.ecommerce.engine.entity.Category;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository repository;
    private final SearchService<Category, CategoryGridDto> searchService;

    public CategoryResponseDto get(long id) {
        Category category = findById(id);
        return new CategoryResponseDto(category);
    }

    public CategoryResponseDto save(CategoryRequestDto requestDto) {
        Category category = new Category(requestDto);
        Category saved = repository.save(category);
        return new CategoryResponseDto(saved);
    }

    public CategoryResponseDto update(long id, CategoryRequestDto requestDto) {
        findById(id);

        Category category = new Category(requestDto);
        category.setId(id);
        Category saved = repository.save(category);
        return new CategoryResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private Category findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("category", id));
    }

    public SearchResponse<CategoryGridDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.CATEGORY, Category.class, CategoryGridDto::new);
    }
}
