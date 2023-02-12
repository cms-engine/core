package com.ecommerce.engine.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_currency")
public class Currency {

    @Id
    private String code;
    private String name;
    private String number;

}
