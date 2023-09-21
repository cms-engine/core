package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.dto.common.PaymentMethodDescriptionDto;
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
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "payment_method_description")
@IdClass(PaymentMethodDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethodDescription implements Localable {

    @Id
    @Column(length = 5)
    Locale locale;

    @Id
    @ManyToOne
    PaymentMethod paymentMethod;

    @Column(nullable = false)
    String name;

    public PaymentMethodDescription(PaymentMethodDescriptionDto descriptionDto) {
        locale = descriptionDto.locale();
        name = descriptionDto.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentMethodDescription that = (PaymentMethodDescription) o;
        return locale != null
                && Objects.equals(locale, that.locale)
                && paymentMethod != null
                && Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, paymentMethod);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        PaymentMethod paymentMethod;
    }
}
