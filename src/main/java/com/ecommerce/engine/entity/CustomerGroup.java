package com.ecommerce.engine.entity;

import static com.ecommerce.engine.entity.CustomerGroup.TABLE_NAME;

import com.ecommerce.engine.dto.admin.request.CustomerGroupRequestDto;
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
@Table(name = TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerGroup {

    public static final String TABLE_NAME = "customer_group";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<CustomerGroupDescription> descriptions = new HashSet<>();

    public CustomerGroup(CustomerGroupRequestDto requestDto) {
        descriptions = requestDto.descriptions().stream()
                .map(CustomerGroupDescription::new)
                .peek(desc -> desc.setCustomerGroup(this))
                .collect(Collectors.toSet());
    }

    public CustomerGroup(Long id) {
        this.id = id;
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
        CustomerGroup that = (CustomerGroup) o;
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
