package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
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

    public PaymentMethodDescription(NameDescriptionDto descriptionDto) {
        locale = descriptionDto.locale();
        name = descriptionDto.name();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        PaymentMethod paymentMethod;
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
        PaymentMethodDescription that = (PaymentMethodDescription) o;
        return getLocale() != null
                && Objects.equals(getLocale(), that.getLocale())
                && getPaymentMethod() != null
                && Objects.equals(getPaymentMethod(), that.getPaymentMethod());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(locale, paymentMethod);
    }
}
