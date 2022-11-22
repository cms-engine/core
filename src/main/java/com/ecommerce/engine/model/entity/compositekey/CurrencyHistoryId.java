package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Currency;
import com.ecommerce.engine.model.entity.Language;
import com.ecommerce.engine.model.entity.Product;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
public class CurrencyHistoryId implements Serializable {

    @ManyToOne
    @JoinColumn
    private Currency currency;
    private LocalDate date;

}
