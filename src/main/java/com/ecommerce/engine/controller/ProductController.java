package com.ecommerce.engine.controller;

import com.ecommerce.engine.dto.request.ProductRequestDto;
import com.ecommerce.engine.dto.response.ProductGridResponseDto;
import com.ecommerce.engine.dto.response.ProductResponseDto;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.model.SearchResponse;
import com.ecommerce.engine.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/search")
    public SearchResponse<ProductGridResponseDto> getGridPage(@Valid @RequestBody SearchRequest searchRequest) {
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

    @DeleteMapping("/delete")
    public void deleteMany(@RequestBody Set<Long> ids) {
        productService.deleteMany(ids);
    }
}
