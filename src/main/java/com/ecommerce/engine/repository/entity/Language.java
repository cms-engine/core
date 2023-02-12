package com.ecommerce.engine.repository.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String alpha2;
    private String code;
    private String locale;
    @ManyToOne
    @JoinColumn
    private Image image;
    private Boolean isActive;


}
