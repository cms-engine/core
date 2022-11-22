package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.CurrencyHistoryId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_currency_history")
public class CurrencyHistory {

    @EmbeddedId
    private CurrencyHistoryId id;
    private Double value;

}
