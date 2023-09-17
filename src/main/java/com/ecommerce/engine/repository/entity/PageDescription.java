package com.ecommerce.engine.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "page_description")
@IdClass(PageDescription.EntityId.class)
public class PageDescription {

    @Id
    private Locale locale;

    @Id
    @ManyToOne
    @JoinColumn
    private Page page;

    private String title;

    @Lob
    private String description;

    private String metaTitle;
    private String metaDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PageDescription that = (PageDescription) o;
        return locale != null && Objects.equals(locale, that.locale) && page != null && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, page);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        Page page;
    }
}
