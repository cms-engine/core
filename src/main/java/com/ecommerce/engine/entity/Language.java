package com.ecommerce.engine.entity;

import static com.ecommerce.engine.entity.Language.TABLE_NAME;

import com.ecommerce.engine.dto.admin.request.LanguageRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Locale;
import java.util.Objects;
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
@Table(name = TABLE_NAME)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Language {

    public static final String TABLE_NAME = "language";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false, unique = true, length = 5)
    Locale hreflang;

    @Column(length = 5)
    String subFolder;

    @Column(length = 5)
    String urlSuffix;

    int sortOrder;

    boolean enabled;

    public Language(Integer id) {
        this.id = id;
    }

    public Language(LanguageRequestDto requestDto) {
        name = requestDto.name();
        hreflang = requestDto.hreflang();
        subFolder = requestDto.subFolder();
        urlSuffix = requestDto.urlSuffix();
        sortOrder = requestDto.sortOrder();
        enabled = requestDto.enabled();
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
        Language language = (Language) o;
        return getId() != null && Objects.equals(getId(), language.getId());
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
