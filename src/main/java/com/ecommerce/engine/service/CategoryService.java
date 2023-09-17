package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.request.CategoryRequestDto;
import com.ecommerce.engine.dto.response.CategoryResponseDto;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.mapper.CategoryMapper;
import com.ecommerce.engine.repository.CategoryRepository;
import com.ecommerce.engine.repository.entity.Category;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDto get(long id) {
        Category category = findById(id);
        return categoryMapper.toDto(category);
    }

    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toEntity(categoryRequestDto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    public CategoryResponseDto update(long id, CategoryRequestDto categoryRequestDto) {
        findById(id);

        Category category = categoryMapper.toEntity(categoryRequestDto);
        category.setId(id);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        categoryRepository.deleteAllById(ids);
    }

    private Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("category", id));
    }
}
