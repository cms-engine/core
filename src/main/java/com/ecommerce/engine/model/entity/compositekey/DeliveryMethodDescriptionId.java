package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.DeliveryMethod;
import com.ecommerce.engine.model.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMethodDescriptionId implements Serializable {

    private Language language;
    private DeliveryMethod deliveryMethod;

}
