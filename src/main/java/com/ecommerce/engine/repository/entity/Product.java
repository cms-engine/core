package com.ecommerce.engine.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn
    @NotNull
    private Category category;
    @Column(unique = true, length = 64)
    private String sku;
    @Column(length = 14)
    private String ean;
    @Column(length = 64)
    private String barcode;
    @OneToOne
    @JoinColumn
    private Brand brand;
    @OneToOne
    @JoinColumn
    private com.ecommerce.engine.repository.entity.Image image;
    @OneToOne
    @JoinColumn
    private StoreStatus availableStatus;
    @OneToOne
    @JoinColumn
    private StoreStatus notAvailableStatus;
    @OneToOne
    @JoinColumn
    private Currency currency;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;
    @Column(precision = 15, scale = 3)
    private BigDecimal quantity;
    @Column(precision = 15, scale = 8)
    private BigDecimal length;
    @Column(precision = 15, scale = 8)
    private BigDecimal width;
    @Column(precision = 15, scale = 8)
    private BigDecimal height;
    @Column(precision = 15, scale = 8)
    private BigDecimal weight;
    private Integer sortOrder;
    @CreationTimestamp
    private LocalDate dateAdded;
    @UpdateTimestamp
    private LocalDate dateModified;
    private Boolean status;

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
