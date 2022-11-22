package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Category;
import com.ecommerce.engine.model.entity.Language;
import com.ecommerce.engine.model.entity.Product;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class ProductDescriptionId implements Serializable {

    @ManyToOne
    @JoinColumn
    private Language language;

    @ManyToOne
    @JoinColumn
    private Product product;

}
