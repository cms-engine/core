package com.ecommerce.engine.repository.entity;

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
    @ManyToOne
    @JoinColumn
    private Category parent;
    @ManyToOne
    @JoinColumn
    private Image image;
    private Integer orderNumber;
    private Boolean isActive;

}
