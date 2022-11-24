package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.SeoUrlId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_seo_url")
@IdClass(SeoUrlId.class)
public class SeoUrl {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    private String route;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeoUrl seoUrl = (SeoUrl) o;
        return language != null && Objects.equals(language, seoUrl.language)
                && route != null && Objects.equals(route, seoUrl.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, route);
    }
}
