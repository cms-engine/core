package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Product;
import com.ecommerce.engine.repository.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierPriceId implements Serializable {

    private Supplier supplier;
    private Product product;

}
