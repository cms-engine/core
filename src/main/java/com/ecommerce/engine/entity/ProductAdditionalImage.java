package com.ecommerce.engine.entity;

import static com.ecommerce.engine.util.NullUtils.nullable;

import com.ecommerce.engine.dto.admin.request.ProductRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "product_additional_image")
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(ProductAdditionalImage.EntityId.class)
public class ProductAdditionalImage {

    @Id
    @ManyToOne
    @JoinColumn
    Product product;

    @Id
    @ManyToOne
    @JoinColumn
    Image image;

    int sortOrder;

    public ProductAdditionalImage(ProductRequestDto.AdditionalImage additionalImage) {
        image = new Image(additionalImage.id());
        sortOrder = additionalImage.sortOrder();
    }

    public UUID getImageId() {
        return nullable(image, Image::getId);
    }

    public String getImageSrc() {
        return nullable(image, Image::getSrc);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Product product;
        Image image;
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
        ProductAdditionalImage that = (ProductAdditionalImage) o;
        return getProduct() != null
                && Objects.equals(getProduct(), that.getProduct())
                && getImage() != null
                && Objects.equals(getImage(), that.getImage());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(product, image);
    }
}
