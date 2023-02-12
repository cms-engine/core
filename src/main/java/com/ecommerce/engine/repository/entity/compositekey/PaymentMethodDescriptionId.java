package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Language;
import com.ecommerce.engine.repository.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDescriptionId implements Serializable {

    private Language language;
    private PaymentMethod paymentMethod;

}
