package com.ecommerce.engine.entity;

import com.ecommerce.engine.admin.dto.common.NameDescriptionDto;
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
@Table(name = DeliveryMethodDescription.TABLE_NAME)
@IdClass(DeliveryMethodDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryMethodDescription extends NameDescriptionSuperclass {

    public static final String TABLE_NAME = "delivery_method_description";

    @Id
    @ManyToOne
    DeliveryMethod deliveryMethod;

    public DeliveryMethodDescription(NameDescriptionDto descriptionDto) {
        super(descriptionDto);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Integer languageId;
        DeliveryMethod deliveryMethod;
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
        DeliveryMethodDescription that = (DeliveryMethodDescription) o;
        return getLanguageId() != null
                && Objects.equals(getLanguageId(), that.getLanguageId())
                && getDeliveryMethod() != null
                && Objects.equals(getDeliveryMethod(), that.getDeliveryMethod());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getLanguageId(), getDeliveryMethod());
    }
}
