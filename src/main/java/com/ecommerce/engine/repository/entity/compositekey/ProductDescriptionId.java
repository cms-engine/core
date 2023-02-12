package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Language;
import com.ecommerce.engine.repository.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescriptionId implements Serializable {

    private Language language;
    private Product product;

}
