package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.grid.CategoryGridDto;
import com.ecommerce.engine.dto.admin.request.CategoryRequestDto;
import com.ecommerce.engine.dto.admin.response.CategoryResponseDto;
import com.ecommerce.engine.entity.Category;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CategoryRepository;
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
public class CategoryService implements EntityPresenceService<Long> {

    private final CategoryRepository repository;
    private final SearchService<Category, CategoryGridDto> searchService;
    private final ForeignKeysChecker foreignKeysChecker;

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
        foreignKeysChecker.checkUsages(Category.TABLE_NAME, id, "category_description");
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private Category findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Category.TABLE_NAME, id));
    }

    public SearchResponse<CategoryGridDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.CATEGORY, Category.class, CategoryGridDto::new);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CATEGORY;
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
