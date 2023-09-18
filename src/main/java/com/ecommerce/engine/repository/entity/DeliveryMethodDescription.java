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
import org.hibernate.Hibernate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeliveryMethodDescription that = (DeliveryMethodDescription) o;
        return locale != null
                && Objects.equals(locale, that.locale)
                && deliveryMethod != null
                && Objects.equals(deliveryMethod, that.deliveryMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, deliveryMethod);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        DeliveryMethod deliveryMethod;
    }
}
