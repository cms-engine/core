package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.annotation.SeoUrlRemove;
import com.ecommerce.engine.dto.admin.grid.ProductGridDto;
import com.ecommerce.engine.dto.admin.request.ProductRequestDto;
import com.ecommerce.engine.dto.admin.response.ProductResponseDto;
import com.ecommerce.engine.entity.Product;
import com.ecommerce.engine.enums.SeoUrlEntity;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.ProductRepository;
import com.ecommerce.engine.search.SearchEntity;
import com.ecommerce.engine.search.SearchRequest;
import com.ecommerce.engine.search.SearchResponse;
import com.ecommerce.engine.search.SearchService;
import com.ecommerce.engine.service.ForeignKeysChecker;
import jakarta.transaction.Transactional;
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
    private final ProductAttributeService productAttributeService;

    public ProductResponseDto get(long id) {
        Product category = findById(id);
        return new ProductResponseDto(category);
    }

    public ProductResponseDto save(ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        productAttributeService.validateProductAttributesMatchingCategoryRequirements(product);

        Product saved = repository.save(product);
        return new ProductResponseDto(saved);
    }

    public ProductResponseDto update(long id, ProductRequestDto requestDto) {
        findById(id);

        Product product = new Product(requestDto);
        productAttributeService.validateProductAttributesMatchingCategoryRequirements(product);

        product.setId(id);
        Product saved = repository.save(product);
        return new ProductResponseDto(saved);
    }

    @Transactional
    @SeoUrlRemove(SeoUrlEntity.PRODUCT)
    public void delete(long id) {
        foreignKeysChecker.checkUsages(Product.TABLE_NAME, id);
        repository.deleteById(id);
    }

    @Transactional
    @SeoUrlRemove(SeoUrlEntity.PRODUCT)
    public void delete(Set<Long> ids) {
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
