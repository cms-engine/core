package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.CategoryDescriptionId;
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
@Table(name = "e_category_description")
@IdClass(CategoryDescriptionId.class)
public class CategoryDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private Category category;
    private String title;
    @Lob
    private String description;
    private String metaTitle;
    private String metaDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryDescription that = (CategoryDescription) o;
        return language != null && Objects.equals(language, that.language)
                && category != null && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, category);
    }
}
