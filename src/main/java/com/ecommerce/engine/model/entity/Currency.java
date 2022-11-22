package com.ecommerce.engine.model.entity;

import lombok.*;

import javax.persistence.*;

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
