package com.ecommerce.engine.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = ProductAdditionalCategory.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(ProductAdditionalCategory.EntityId.class)
public class ProductAdditionalCategory {

    public static final String TABLE_NAME = "product_additional_category";

    @Id
    @ManyToOne
    @JoinColumn
    Product product;

    @Id
    @ManyToOne
    @JoinColumn
    Category category;

    public ProductAdditionalCategory(Long categoryId) {
        category = new Category(categoryId);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Product product;
        Category category;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProductAdditionalCategory that = (ProductAdditionalCategory) o;
        return getProduct() != null
                && Objects.equals(getProduct(), that.getProduct())
                && getCategory() != null
                && Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(product, category);
    }
}
