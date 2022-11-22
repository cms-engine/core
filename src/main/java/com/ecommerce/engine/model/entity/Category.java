package com.ecommerce.engine.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn
    private Category parent;
    @OneToOne
    @JoinColumn
    private Image image;
    private Integer orderNumber;
    private Boolean isActive;

}
