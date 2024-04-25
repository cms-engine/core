package com.ecommerce.engine.entity;

import com.ecommerce.engine.enums.SeoUrlType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = SeoUrl.TABLE_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeoUrl {

    public static final String TABLE_NAME = "seo_url";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SeoUrlType type;

    long recordId;

    int languageId;

    @Column(nullable = false, unique = true)
    String path;
}
