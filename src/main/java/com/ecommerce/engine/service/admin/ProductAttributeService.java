package com.ecommerce.engine.service.admin;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.dto.admin.response.ProductAvailableAttributeDto;
import com.ecommerce.engine.entity.*;
import com.ecommerce.engine.exception.NotFoundException;
import com.ecommerce.engine.repository.CategoryRepository;
import com.ecommerce.engine.repository.ProductRepository;
import com.ecommerce.engine.util.TranslationUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Collection<ProductAvailableAttributeDto> getPrefillAttributes(long productId) {
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

    public void validateProductAttributesMatchingCategoryRequirements(Product product) {
        Set<Long> categoryIds = product.getAdditionalCategories().stream()
                .map(ProductAdditionalCategory::getCategory)
                .map(Category::getId)
                .collect(Collectors.toSet());
        categoryIds.add(product.getCategory().getId());

        List<Category> categories = categoryRepository.findAllById(categoryIds);

        Set<Long> mandatoryAttributes = categories.stream()
                .map(Category::getAttributes)
                .flatMap(Collection::stream)
                .filter(CategoryAttribute::isMandatory)
                .map(CategoryAttribute::getAttribute)
                .map(Attribute::getId)
                .collect(Collectors.toSet());

        Set<Long> productAttributes = product.getAttributes().stream()
                .map(ProductAttribute::getAttribute)
                .map(Attribute::getId)
                .collect(Collectors.toSet());

        if (productAttributes.containsAll(mandatoryAttributes)) {
            return;
        }

        Set<Long> missingIds = new HashSet<>(mandatoryAttributes);
        missingIds.removeAll(productAttributes);

        throw new ApplicationException(
                ErrorCode.INVALID_CONFIGURATION,
                TranslationUtils.getMessage("exception.productAttributesMismatch", missingIds));
    }

    private Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(Product.TABLE_NAME, id));
    }
}
