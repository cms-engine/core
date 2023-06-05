package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.DeliveryMethodDescriptionId;
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
@Table(name = "e_delivery_method_description")
@IdClass(DeliveryMethodDescriptionId.class)
public class DeliveryMethodDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private DeliveryMethod deliveryMethod;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeliveryMethodDescription that = (DeliveryMethodDescription) o;
        return language != null && Objects.equals(language, that.language)
                && deliveryMethod != null && Objects.equals(deliveryMethod, that.deliveryMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, deliveryMethod);
    }
}
