package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.request.PurchaseOrderStatusRequestDto;
import io.github.lipiridi.searchengine.Searchable;
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
@Table(name = PurchaseOrderStatus.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderStatus {

    public static final String TABLE_NAME = "purchase_order_status";

    @Searchable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ToString.Exclude
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "purchaseOrderStatus",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    Set<PurchaseOrderStatusDescription> descriptions = new HashSet<>();

    public PurchaseOrderStatus(PurchaseOrderStatusRequestDto requestDto) {
        descriptions = requestDto.descriptions().stream()
                .map(PurchaseOrderStatusDescription::new)
                .peek(desc -> desc.setPurchaseOrderStatus(this))
                .collect(Collectors.toSet());
    }

    public PurchaseOrderStatus(Long id) {
        this.id = id;
    }

    public String getLocaleName() {
        return HasLocale.getStoreDefaultLocaleName(descriptions);
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
        PurchaseOrderStatus that = (PurchaseOrderStatus) o;
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
