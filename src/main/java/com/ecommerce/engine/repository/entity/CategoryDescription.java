package com.ecommerce.engine.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Locale;

@Getter
@Setter
@ToString
@Entity
@Table(name = "category_description")
@IdClass(CategoryDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDescription {

    @Id
    Locale locale;

    @Id
    @ManyToOne
    Category category;

    String title;

    @Column(columnDefinition = "text")
    String description;

    String metaTitle;
    String metaDescription;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        Category category;
    }
}
