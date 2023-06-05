package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.PageDescriptionId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_page_description")
@IdClass(PageDescriptionId.class)
public class PageDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
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
        return language != null && Objects.equals(language, that.language)
                && page != null && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, page);
    }
}
