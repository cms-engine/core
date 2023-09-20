package com.ecommerce.engine.repository.entity;

import static com.ecommerce.engine.util.NullUtils.nullable;

import com.ecommerce.engine.dto.request.ProductRequestDto;
import com.ecommerce.engine.enums.LengthClass;
import com.ecommerce.engine.enums.WeightClass;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    @NotNull Category category;

    @ManyToOne
    @JoinColumn
    Image image;

    @Column(unique = true, length = 64)
    String sku;

    @Column(length = 14)
    String ean;

    @Column(length = 64)
    String barcode;

    @ManyToOne
    @JoinColumn
    Brand brand;

    /*@Column(length = 3)
    Currency currency;

    @Column(precision = 15, scale = 2)
    BigDecimal price;

    @Column(precision = 15, scale = 3)
    BigDecimal quantity;*/

    @Enumerated(EnumType.STRING)
    LengthClass lengthClass;

    @Column(precision = 15, scale = 8)
    BigDecimal length;

    @Column(precision = 15, scale = 8)
    BigDecimal width;

    @Column(precision = 15, scale = 8)
    BigDecimal height;

    @Enumerated(EnumType.STRING)
    WeightClass weightClass;

    @Column(precision = 15, scale = 8)
    BigDecimal weight;

    @CreationTimestamp
    Instant created;

    @UpdateTimestamp
    Instant updated;

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<ProductDescription> descriptions = new HashSet<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<ProductAdditionalImage> additionalImages = new HashSet<>();

    public Product(ProductRequestDto requestDto) {
        if (requestDto.categoryId() != null) {
            category = new Category(requestDto.categoryId());
        }
        if (requestDto.imageId() != null) {
            image = new Image(requestDto.imageId());
        }
        if (requestDto.brandId() != null) {
            brand = new Brand(requestDto.brandId());
        }
        sku = requestDto.sku();
        ean = requestDto.ean();
        barcode = requestDto.barcode();
        lengthClass = requestDto.lengthClass();
        length = requestDto.length();
        width = requestDto.width();
        height = requestDto.height();
        weightClass = requestDto.weightClass();
        weight = requestDto.weight();
        enabled = requestDto.enabled();

        descriptions = requestDto.descriptions().stream()
                .map(ProductDescription::new)
                .peek(desc -> desc.setProduct(this))
                .collect(Collectors.toSet());

        additionalImages = requestDto.additionalImages().stream()
                .map(ProductAdditionalImage::new)
                .peek(img -> img.setProduct(this))
                .collect(Collectors.toSet());
    }

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

    public String getLocaleTitle() {
        return DescriptionSuperclass.getLocaleTitle(descriptions);
    }

    public Long getCategoryId() {
        return nullable(category, Category::getId);
    }

    public String getCategoryLocaleTitle() {
        return nullable(category, Category::getLocaleTitle);
    }

    public Long getBrandId() {
        return nullable(brand, Brand::getId);
    }

    public String getBrandName() {
        return nullable(brand, Brand::getName);
    }

    public UUID getImageId() {
        return nullable(image, Image::getId);
    }

    public String getImageSrc() {
        return nullable(image, Image::getSrc);
    }
}
