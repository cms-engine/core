package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.dto.common.DescriptionDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Locale;
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
@Table(name = "category_description")
@NoArgsConstructor
@IdClass(CategoryDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDescription extends DescriptionSuperclass {

    @Id
    @ManyToOne
    Category category;

    public CategoryDescription(DescriptionDto descriptionDto) {
        super(descriptionDto);
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
        CategoryDescription that = (CategoryDescription) o;
        return getCategory() != null
                && Objects.equals(getCategory(), that.getCategory())
                && getLocale() != null
                && Objects.equals(getLocale(), that.getLocale());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getCategory(), getLocale());
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        Category category;
    }
}
