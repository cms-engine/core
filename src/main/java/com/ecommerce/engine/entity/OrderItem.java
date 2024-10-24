package com.ecommerce.engine.entity;

import com.ecommerce.engine.admin.dto.request.PurchaseOrderRequestDto;
import io.github.lipiridi.searchengine.Searchable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
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
@Table(name = OrderItem.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {

    public static final String TABLE_NAME = "order_item";

    @Searchable
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Searchable
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    PurchaseOrder purchaseOrder;

    @Searchable
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    Product product;

    @Column(nullable = false, precision = 15, scale = 3)
    BigDecimal price;

    @Column(nullable = false, precision = 15, scale = 3)
    BigDecimal quantity;

    @Column(nullable = false, precision = 15, scale = 3)
    BigDecimal cost;

    public OrderItem(PurchaseOrderRequestDto.OrderItem requestDto) {
        product = new Product(requestDto.productId());
        price = requestDto.price();
        quantity = requestDto.quantity();
        cost = requestDto.cost();
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
        OrderItem that = (OrderItem) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this)
                        .getHibernateLazyInitializer()
                        .getPersistentClass()
                        .hashCode()
                : getClass().hashCode();
    }
}
