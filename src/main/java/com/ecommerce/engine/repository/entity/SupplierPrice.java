package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.SupplierPriceId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_supplier_price")
@IdClass(SupplierPriceId.class)
public class SupplierPrice {

    @Id
    @ManyToOne
    @JoinColumn
    private Supplier supplier;
    @Id
    @ManyToOne
    @JoinColumn
    private Product product;
    @OneToOne
    @JoinColumn
    private Currency currency;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SupplierPrice that = (SupplierPrice) o;
        return supplier != null && Objects.equals(supplier, that.supplier)
                && product != null && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, product);
    }
}
