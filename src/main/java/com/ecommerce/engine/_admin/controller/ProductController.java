package com.ecommerce.engine._admin.controller;

import com.ecommerce.engine._admin.dto.grid.ProductGridDto;
import com.ecommerce.engine._admin.dto.request.ProductRequestDto;
import com.ecommerce.engine._admin.dto.response.ProductAvailableAttributeDto;
import com.ecommerce.engine._admin.dto.response.ProductResponseDto;
import com.ecommerce.engine._admin.service.ProductAttributeService;
import com.ecommerce.engine._admin.service.ProductService;
import io.github.lipiridi.searchengine.dto.SearchRequest;
import io.github.lipiridi.searchengine.dto.SearchResponse;
import jakarta.validation.Valid;
import java.util.Collection;
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
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductAttributeService productAttributeService;

    @PostMapping("/search")
    public SearchResponse<ProductGridDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
        return productService.search(searchRequest);
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable long id) {
        return productService.get(id);
    }

    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto requestDto) {
        return productService.save(requestDto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable long id, @Valid @RequestBody ProductRequestDto requestDto) {
        return productService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        productService.delete(id);
    }

    @PostMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        productService.deleteMany(ids);
    }

    @GetMapping("/{id}/prefill-attributes")
    public Collection<ProductAvailableAttributeDto> getPrefillAttributes(@PathVariable long id) {
        return productAttributeService.getPrefillAttributes(id);
    }
}
