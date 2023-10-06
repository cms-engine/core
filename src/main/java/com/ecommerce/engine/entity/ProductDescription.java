package com.ecommerce.engine.entity;

import static com.ecommerce.engine.entity.ProductDescription.TABLE_NAME;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
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
@Table(name = TABLE_NAME)
@IdClass(ProductDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDescription extends DescriptionSuperclass {

    public static final String TABLE_NAME = "product_description";

    @Id
    @ManyToOne
    Product product;

    public ProductDescription(MetaDescriptionDto metaDescriptionDto) {
        super(metaDescriptionDto);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Integer languageId;
        Product product;
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
        ProductDescription that = (ProductDescription) o;
        return getProduct() != null
                && Objects.equals(getProduct(), that.getProduct())
                && getLanguageId() != null
                && Objects.equals(getLanguageId(), that.getLanguageId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getProduct(), getLanguageId());
    }
}
