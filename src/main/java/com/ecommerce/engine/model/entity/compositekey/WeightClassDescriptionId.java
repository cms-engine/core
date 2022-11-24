package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Language;
import com.ecommerce.engine.model.entity.WeightClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightClassDescriptionId implements Serializable {

    private Language language;
    private WeightClass weightClass;

}
