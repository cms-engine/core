package com.ecommerce.engine.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn
    @NotNull
    private Category category;
    @OneToOne
    @JoinColumn
    private Image image;
    @PositiveOrZero
    private Double price;
    private Double amount;
    private Boolean isActive;

}
