package com.ecommerce.engine.controller;

import com.ecommerce.engine.dto.request.BrandRequestDto;
import com.ecommerce.engine.dto.response.BrandResponseDto;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.service.BrandService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
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
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/search/{id}")
    public SearchResponse<BrandResponseDto> getGridPageCache(@PathVariable UUID id) {
        return brandService.search(id, null);
    }

    @PostMapping("/search")
    public SearchResponse<BrandResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return brandService.search(null, searchRequest);
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
