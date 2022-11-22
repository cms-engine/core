package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.CategoryDescriptionId;
import com.ecommerce.engine.model.entity.compositekey.ProductDescriptionId;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_product_description")
public class ProductDescription {

    @EmbeddedId
    private ProductDescriptionId id;
    private String title;
    @Length
    private String description;
    private String metaTitle;
    private String metaDescription;

}
