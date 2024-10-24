package com.ecommerce.engine.entity;

import com.ecommerce.engine._admin.dto.common.NameDescriptionDto;
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
@Table(name = CustomerGroupDescription.TABLE_NAME)
@IdClass(CustomerGroupDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerGroupDescription extends NameDescriptionSuperclass {

    public static final String TABLE_NAME = "customer_group_description";

    @Id
    @ManyToOne
    CustomerGroup customerGroup;

    public CustomerGroupDescription(NameDescriptionDto descriptionDto) {
        super(descriptionDto);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Integer languageId;
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
        return getLanguageId() != null
                && Objects.equals(getLanguageId(), that.getLanguageId())
                && getCustomerGroup() != null
                && Objects.equals(getCustomerGroup(), that.getCustomerGroup());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getLanguageId(), getCustomerGroup());
    }
}
