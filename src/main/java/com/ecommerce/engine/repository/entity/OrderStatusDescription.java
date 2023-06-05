package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.OrderStatusDescriptionId;
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
@Table(name = "e_order_status_description")
@IdClass(OrderStatusDescriptionId.class)
public class OrderStatusDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private OrderStatus orderStatus;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderStatusDescription that = (OrderStatusDescription) o;
        return language != null && Objects.equals(language, that.language)
                && orderStatus != null && Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, orderStatus);
    }
}
