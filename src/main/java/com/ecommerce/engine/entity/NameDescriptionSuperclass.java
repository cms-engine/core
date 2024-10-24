package com.ecommerce.engine.entity;

import com.ecommerce.engine._admin.dto.common.NameDescriptionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class NameDescriptionSuperclass implements HasLocale {
    @Id
    Integer languageId;

    @Column(nullable = false)
    String name;

    public NameDescriptionSuperclass(NameDescriptionDto metaDescriptionDto) {
        languageId = metaDescriptionDto.languageId();
        name = metaDescriptionDto.name();
    }
}
