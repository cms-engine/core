package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Category;
import com.ecommerce.engine.model.entity.Language;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class CategoryDescriptionId implements Serializable {

    @ManyToOne
    @JoinColumn
    private Language language;

    @ManyToOne
    @JoinColumn
    private Category category;

}
