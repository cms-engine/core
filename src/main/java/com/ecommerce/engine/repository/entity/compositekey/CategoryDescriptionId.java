package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Category;
import com.ecommerce.engine.repository.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDescriptionId implements Serializable {

    private Language language;
    private Category category;

}
