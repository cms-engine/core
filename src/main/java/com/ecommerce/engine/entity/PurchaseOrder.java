package com.ecommerce.engine.entity;

import static com.ecommerce.engine.util.NullUtils.nullable;

import com.ecommerce.engine._admin.dto.request.PurchaseOrderRequestDto;
import io.github.lipiridi.searchengine.Searchable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = PurchaseOrder.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrder {

    public static final String TABLE_NAME = "purchase_order";

    @Searchable
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Searchable
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    PurchaseOrderStatus status;

    @ManyToOne
    @JoinColumn
    Customer customer;

    String firstName;
    String lastName;
    String middleName;
    String phone;

    @Searchable
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    PaymentMethod paymentMethod;

    @Searchable
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    DeliveryMethod deliveryMethod;

    @Column(length = 500)
    String customerComment;

    @Column(length = 500)
    String managerComment;

    @Column(nullable = false, precision = 15, scale = 3)
    BigDecimal totalCost;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<OrderItem> items = new HashSet<>();

    @Searchable
    @CreationTimestamp
    Instant createdAt;

    @Searchable
    @UpdateTimestamp
    Instant updatedAt;

    public PurchaseOrder(PurchaseOrderRequestDto requestDto) {
        status = new PurchaseOrderStatus(requestDto.statusId());
        customer = new Customer(requestDto.customerId());
        firstName = requestDto.firstName();
        lastName = requestDto.lastName();
        middleName = requestDto.middleName();
        phone = requestDto.phone();
        paymentMethod = new PaymentMethod(requestDto.paymentMethodId());
        deliveryMethod = new DeliveryMethod(requestDto.deliveryMethodId());
        items = requestDto.items().stream()
                .map(OrderItem::new)
                .peek(order -> order.setPurchaseOrder(this))
                .collect(Collectors.toSet());
    }

    public UUID getCustomerId() {
        return nullable(customer, Customer::getId);
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
        PurchaseOrder that = (PurchaseOrder) o;
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
