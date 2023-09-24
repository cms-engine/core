package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.common.NameDescriptionDto;
import jakarta.persistence.Column;
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
@NoArgsConstructor
@Table(name = "customer_group_description")
@IdClass(CustomerGroupDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerGroupDescription implements Localable {

    @Id
    @Column(length = 5)
    Locale locale;

    @Id
    @ManyToOne
    CustomerGroup customerGroup;

    @Column(nullable = false)
    String name;

    public CustomerGroupDescription(NameDescriptionDto descriptionDto) {
        locale = descriptionDto.locale();
        name = descriptionDto.name();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        CustomerGroup customerGroup;
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
        CustomerGroupDescription that = (CustomerGroupDescription) o;
        return getLocale() != null
                && Objects.equals(getLocale(), that.getLocale())
                && getCustomerGroup() != null
                && Objects.equals(getCustomerGroup(), that.getCustomerGroup());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(locale, customerGroup);
    }
}
