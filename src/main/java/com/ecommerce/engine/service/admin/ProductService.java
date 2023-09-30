package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.grid.ProductGridDto;
import com.ecommerce.engine.dto.admin.request.ProductRequestDto;
import com.ecommerce.engine.dto.admin.response.ProductResponseDto;
import com.ecommerce.engine.entity.Product;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.ProductRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final SearchService<Product, ProductGridDto> searchService;
    private final ForeignKeysChecker foreignKeysChecker;

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
        foreignKeysChecker.checkUsages(Product.TABLE_NAME, id);
        repository.deleteById(id);
    }

    public void deleteMany(Set<Long> ids) {
        ids.forEach(id -> foreignKeysChecker.checkUsages(Product.TABLE_NAME, id));
        repository.deleteAllById(ids);
    }

    private Product findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Product.TABLE_NAME, id));
    }

    public SearchResponse<ProductGridDto> search(UUID id, SearchRequest searchRequest) {
        return searchService.search(id, searchRequest, SearchEntity.PRODUCT, Product.class, ProductGridDto::new);
    }
}