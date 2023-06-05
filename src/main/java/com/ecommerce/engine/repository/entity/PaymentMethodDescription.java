package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.PaymentMethodDescriptionId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_payment_method_description")
@IdClass(PaymentMethodDescriptionId.class)
public class PaymentMethodDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private PaymentMethod paymentMethod;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentMethodDescription that = (PaymentMethodDescription) o;
        return language != null && Objects.equals(language, that.language)
                && paymentMethod != null && Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, paymentMethod);
    }
}
