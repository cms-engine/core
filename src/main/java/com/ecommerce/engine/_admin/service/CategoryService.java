package com.ecommerce.engine._admin.service;

import com.ecommerce.engine._admin.annotation.SeoUrlRemove;
import com.ecommerce.engine._admin.dto.grid.CategoryGridDto;
import com.ecommerce.engine._admin.dto.request.CategoryRequestDto;
import com.ecommerce.engine._admin.dto.response.CategoryResponseDto;
import com.ecommerce.engine.entity.Category;
import com.ecommerce.engine.entity.projection.SelectProjection;
import com.ecommerce.engine.enums.SeoUrlType;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CategoryRepository;
import com.ecommerce.engine.service.EntityPresenceService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import com.ecommerce.engine.validation.EntityType;
import io.github.lipiridi.searchengine.SearchService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements EntityPresenceService<Long> {

    private final CategoryRepository repository;
    private final SearchService searchService;
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
        checkExisting(id);

        Category category = new Category(requestDto);
        category.setId(id);
        Category saved = repository.save(category);
        return new CategoryResponseDto(saved);
    }

    @Transactional
    @SeoUrlRemove(SeoUrlType.CATEGORY)
    public void delete(long id) {
        foreignKeysChecker.checkUsages(Category.TABLE_NAME, id);
        repository.deleteById(id);
    }

    @Transactional
    @SeoUrlRemove(SeoUrlType.CATEGORY)
    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Category.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Category findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Category.TABLE_NAME, id));
    }

    private void checkExisting(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(Category.TABLE_NAME, id);
        }
    }

    public SearchResponse<CategoryGridDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, Category.class, CategoryGridDto::new);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CATEGORY;
    }

    @Override
    public List<SelectProjection> findSelectOptions(Pageable pageable, int languageId, @Nullable String search) {
        return repository.findSelectOptions(pageable, languageId, search);
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
