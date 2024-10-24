package com.ecommerce.engine.admin.controller;

import com.ecommerce.engine.admin.dto.request.BrandRequestDto;
import com.ecommerce.engine.admin.dto.response.BrandResponseDto;
import com.ecommerce.engine.admin.service.BrandService;
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
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/search")
    public SearchResponse<BrandResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return brandService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public BrandResponseDto get(@PathVariable long id) {
        return brandService.get(id);
    }

    @PostMapping
    public BrandResponseDto create(@Valid @RequestBody BrandRequestDto requestDto) {
        return brandService.save(requestDto);
    }

    @PutMapping("/{id}")
    public BrandResponseDto update(@PathVariable long id, @Valid @RequestBody BrandRequestDto requestDto) {
        return brandService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        brandService.delete(id);
    }

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        brandService.deleteMany(ids);
    }
}
