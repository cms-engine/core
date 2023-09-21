package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.dto.request.PaymentMethodRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.hibernate.Hibernate;

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

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "paymentMethod", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<PaymentMethodDescription> descriptions = new HashSet<>();

    public PaymentMethod(PaymentMethodRequestDto requestDto) {
        enabled = requestDto.enabled();
        descriptions = requestDto.descriptions().stream()
                .map(PaymentMethodDescription::new)
                .peek(desc -> desc.setPaymentMethod(this))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentMethod that = (PaymentMethod) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getLocaleName() {
        return Localable.getLocaleTitle(descriptions);
    }
}
