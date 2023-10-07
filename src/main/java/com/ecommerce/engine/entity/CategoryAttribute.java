package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.request.CategoryRequestDto;
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
@Table(name = CategoryAttribute.TABLE_NAME)
@NoArgsConstructor
@IdClass(CategoryAttribute.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAttribute {

    public static final String TABLE_NAME = "category_attribute";

    @Id
    @ManyToOne
    Attribute attribute;

    @Id
    @ManyToOne
    Category category;

    boolean mandatory;

    boolean useInFilters;

    int sortOrder;

    public CategoryAttribute(CategoryRequestDto.Attribute requestDto) {
        attribute = new Attribute(requestDto.id());
        mandatory = requestDto.mandatory();
        useInFilters = requestDto.useInFilters();
        sortOrder = requestDto.sortOrder();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Attribute attribute;
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
        CategoryAttribute that = (CategoryAttribute) o;
        return getCategory() != null
                && Objects.equals(getCategory(), that.getCategory())
                && getAttribute() != null
                && Objects.equals(getAttribute(), that.getAttribute());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getCategory(), getAttribute());
    }
}
