package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Currency;
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
