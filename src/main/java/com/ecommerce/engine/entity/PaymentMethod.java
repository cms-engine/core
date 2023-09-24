package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.request.PaymentMethodRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
@Table(name = "payment_method")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    Image image;

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "paymentMethod", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<PaymentMethodDescription> descriptions = new HashSet<>();

    public PaymentMethod(PaymentMethodRequestDto requestDto) {
        if (requestDto.imageId() != null) {
            image = new Image(requestDto.imageId());
        }
        enabled = requestDto.enabled();
        descriptions = requestDto.descriptions().stream()
                .map(PaymentMethodDescription::new)
                .peek(desc -> desc.setPaymentMethod(this))
                .collect(Collectors.toSet());
    }

    public String getLocaleName() {
        return Localable.getLocaleName(descriptions);
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
        PaymentMethod that = (PaymentMethod) o;
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
