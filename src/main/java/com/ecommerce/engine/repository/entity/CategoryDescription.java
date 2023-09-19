package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.dto.common.DescriptionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@Entity
@Table(name = "category_description")
@NoArgsConstructor
@IdClass(CategoryDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDescription {

    @Id
    @Column(length = 5)
    Locale locale;

    @Id
    @ManyToOne
    Category category;

    String title;

    @Column(columnDefinition = "text")
    String description;

    String metaTitle;
    String metaDescription;

    public CategoryDescription(DescriptionDto descriptionDto) {
        locale = descriptionDto.locale();
        title = descriptionDto.title();
        description = descriptionDto.description();
        metaTitle = descriptionDto.metaTitle();
        metaDescription = descriptionDto.metaDescription();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        Category category;
    }
}
