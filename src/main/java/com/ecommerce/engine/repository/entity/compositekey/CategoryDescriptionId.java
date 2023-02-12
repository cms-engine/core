package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Category;
import com.ecommerce.engine.repository.entity.Language;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
