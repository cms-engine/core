package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.CategoryDescriptionId;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

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
