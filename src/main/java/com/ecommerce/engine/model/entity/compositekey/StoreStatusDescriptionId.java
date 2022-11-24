package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Language;
import com.ecommerce.engine.model.entity.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreStatusDescriptionId implements Serializable {

    private Language language;
    private StoreStatus storeStatus;

}
