package com.ecommerce.engine.entity;

import static com.ecommerce.engine.entity.Page.TABLE_NAME;

import com.ecommerce.engine.dto.admin.request.PageRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Page {

    public static final String TABLE_NAME = "page";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean bottom;
    int sortOrder;

    @CreationTimestamp
    Instant created;

    @UpdateTimestamp
    Instant updated;

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "page", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<PageDescription> descriptions = new HashSet<>();

    public Page(PageRequestDto requestDto) {
        bottom = requestDto.bottom();
        sortOrder = requestDto.sortOrder();
        enabled = requestDto.enabled();
        descriptions = requestDto.descriptions().stream()
                .map(PageDescription::new)
                .peek(desc -> desc.setPage(this))
                .collect(Collectors.toSet());
    }

    public String getLocaleTitle() {
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
        Page page = (Page) o;
        return getId() != null && Objects.equals(getId(), page.getId());
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
