package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Language;
import com.ecommerce.engine.model.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDescriptionId implements Serializable {

    private Language language;
    private OrderStatus orderStatus;

}
