package com.ecommerce.engine.repository.entity;

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
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@Table(name = "delivery_method_description")
@IdClass(DeliveryMethodDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryMethodDescription {

    @Id
    @Column(length = 5)
    Locale locale;

    @Id
    @ManyToOne
    DeliveryMethod deliveryMethod;

    String name;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
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
        return getLocale() != null
                && Objects.equals(getLocale(), that.getLocale())
                && getDeliveryMethod() != null
                && Objects.equals(getDeliveryMethod(), that.getDeliveryMethod());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(locale, deliveryMethod);
    }
}
