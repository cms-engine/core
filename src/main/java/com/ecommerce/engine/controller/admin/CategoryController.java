package com.ecommerce.engine.controller.admin;

import com.ecommerce.engine.dto.admin.grid.CategoryGridDto;
import com.ecommerce.engine.dto.admin.request.CategoryRequestDto;
import com.ecommerce.engine.dto.admin.response.CategoryResponseDto;
import com.ecommerce.engine.service.admin.CategoryService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/search")
    public SearchResponse<CategoryGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return categoryService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public CategoryResponseDto get(@PathVariable long id) {
        return categoryService.get(id);
    }

    @PostMapping
    public CategoryResponseDto create(@Valid @RequestBody CategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(@PathVariable long id, @Valid @RequestBody CategoryRequestDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        categoryService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        categoryService.deleteMany(ids);
    }
}
