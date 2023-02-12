package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Language;
import com.ecommerce.engine.repository.entity.Product;
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
