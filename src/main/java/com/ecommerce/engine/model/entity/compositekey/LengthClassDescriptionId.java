package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Language;
import com.ecommerce.engine.model.entity.LengthClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LengthClassDescriptionId implements Serializable {

    private Language language;
    private LengthClass lengthClass;

}
