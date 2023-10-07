package com.ecommerce.engine.entity;

import static com.ecommerce.engine.entity.PaymentMethodDescription.TABLE_NAME;

import com.ecommerce.engine.dto.admin.common.NameDescriptionDto;
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
@IdClass(PaymentMethodDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethodDescription extends NameDescriptionSuperclass {

    public static final String TABLE_NAME = "payment_method_description";

    @Id
    @ManyToOne
    PaymentMethod paymentMethod;

    public PaymentMethodDescription(NameDescriptionDto descriptionDto) {
        super(descriptionDto);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Integer languageId;
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
        return getLanguageId() != null
                && Objects.equals(getLanguageId(), that.getLanguageId())
                && getPaymentMethod() != null
                && Objects.equals(getPaymentMethod(), that.getPaymentMethod());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getLanguageId(), paymentMethod);
    }
}
