package com.ecommerce.engine.entity;

import com.ecommerce.engine._admin.dto.request.AttributeRequestDto;
import com.ecommerce.engine.enums.InputType;
import io.github.lipiridi.searchengine.Searchable;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = Attribute.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attribute {

    public static final String TABLE_NAME = "attribute";

    @Searchable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    InputType inputType;

    boolean enabled;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "attribute", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<AttributeDescription> descriptions = new HashSet<>();

    public Attribute(AttributeRequestDto requestDto) {
        inputType = requestDto.inputType();
        enabled = requestDto.enabled();
        descriptions = requestDto.descriptions().stream()
                .map(AttributeDescription::new)
                .peek(desc -> desc.setAttribute(this))
                .collect(Collectors.toSet());
    }

    public Attribute(Long id) {
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
        Attribute that = (Attribute) o;
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
