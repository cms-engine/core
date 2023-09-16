package com.ecommerce.engine.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product_description")
@IdClass(ProductDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDescription {

    @Id
    Locale locale;

    @Id
    @ManyToOne
    @JoinColumn
    Product product;

    String title;

    @Lob
    String description;

    String metaTitle;
    String metaDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductDescription that = (ProductDescription) o;
        return locale != null
                && Objects.equals(locale, that.locale)
                && product != null
                && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, product);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        Product product;
    }
}
