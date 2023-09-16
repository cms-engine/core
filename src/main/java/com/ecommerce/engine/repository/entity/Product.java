package com.ecommerce.engine.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    @NotNull Category category;

    @Column(unique = true, length = 64)
    String sku;

    @Column(length = 14)
    String ean;

    @Column(length = 64)
    String barcode;

    @ManyToOne
    @JoinColumn
    Brand brand;

    String currency;

    @Column(precision = 15, scale = 2)
    BigDecimal price;

    @Column(precision = 15, scale = 3)
    BigDecimal quantity;

    @Column(precision = 15, scale = 8)
    BigDecimal length;

    @Column(precision = 15, scale = 8)
    BigDecimal width;

    @Column(precision = 15, scale = 8)
    BigDecimal height;

    @Column(precision = 15, scale = 8)
    BigDecimal weight;

    Integer sortOrder;

    @CreationTimestamp
    LocalDate created;

    @UpdateTimestamp
    LocalDate updated;

    boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product that = (Product) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
