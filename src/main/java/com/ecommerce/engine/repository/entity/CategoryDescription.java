package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.CategoryDescriptionId;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_category_description")
public class CategoryDescription {

    @EmbeddedId
    private CategoryDescriptionId id;
    private String title;
    @Length
    private String description;
    private String metaTitle;
    private String metaDescription;

}
