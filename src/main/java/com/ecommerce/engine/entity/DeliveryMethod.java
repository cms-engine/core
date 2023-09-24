package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.request.DeliveryMethodRequestDto;
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
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "delivery_method")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deliveryMethod", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<DeliveryMethodDescription> descriptions = new HashSet<>();

    public DeliveryMethod(DeliveryMethodRequestDto requestDto) {
        enabled = requestDto.enabled();
        descriptions = requestDto.descriptions().stream()
                .map(DeliveryMethodDescription::new)
                .peek(desc -> desc.setDeliveryMethod(this))
                .collect(Collectors.toSet());
    }

    public String getLocaleName() {
        return Localable.getLocaleTitle(descriptions);
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
        DeliveryMethod that = (DeliveryMethod) o;
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
