package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.dto.admin.response.ProductAvailableAttributeDto;
import com.ecommerce.engine.entity.Category;
import com.ecommerce.engine.entity.CategoryAttribute;
import com.ecommerce.engine.entity.Product;
import com.ecommerce.engine.entity.ProductAdditionalCategory;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.ProductRepository;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductRepository repository;

    public Collection<ProductAvailableAttributeDto> getAvailableAttributes(long productId) {
        Product product = findById(productId);

        Set<CategoryAttribute> categoryAttributes = product.getAdditionalCategories().stream()
                .map(ProductAdditionalCategory::getCategory)
                .map(Category::getAttributes)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        categoryAttributes.addAll(product.getCategory().getAttributes());

        return categoryAttributes.stream()
                .map(ProductAvailableAttributeDto::new)
                .collect(Collectors.toSet());
    }

    private Product findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Product.TABLE_NAME, id));
    }
}
