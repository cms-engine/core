package com.ecommerce.engine.service;

import com.ecommerce.engine.dto.request.ProductRequestDto;
import com.ecommerce.engine.dto.response.ProductGridResponseDto;
import com.ecommerce.engine.dto.response.ProductResponseDto;
import com.ecommerce.engine.enums.SearchEntity;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.model.SearchRequest;
import com.ecommerce.engine.model.SearchResponse;
import com.ecommerce.engine.repository.ProductRepository;
import com.ecommerce.engine.repository.entity.Product;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final SearchService<Product, ProductGridResponseDto> searchService;

    public ProductResponseDto get(long id) {
        Product category = findById(id);
        return new ProductResponseDto(category);
    }

    public ProductResponseDto save(ProductRequestDto requestDto) {
        Product category = new Product(requestDto);
        Product saved = repository.save(category);
        return new ProductResponseDto(saved);
    }

    public ProductResponseDto update(long id, ProductRequestDto requestDto) {
        findById(id);

        Product category = new Product(requestDto);
        category.setId(id);
        Product saved = repository.save(category);
        return new ProductResponseDto(saved);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        repository.deleteAllById(ids);
    }

    private Product findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("product", id));
    }

    public SearchResponse<ProductGridResponseDto> search(SearchRequest searchRequest) {
        return searchService.search(searchRequest, SearchEntity.PRODUCT, Product.class, ProductGridResponseDto::new);
    }
}
