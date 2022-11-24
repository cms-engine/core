package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.ProductDescriptionId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_product_description")
@IdClass(ProductDescriptionId.class)
public class ProductDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private Product product;
    private String title;
    @Lob
    private String description;
    private String metaTitle;
    private String metaDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductDescription that = (ProductDescription) o;
        return language != null && Objects.equals(language, that.language)
                && product != null && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, product);
    }
}
