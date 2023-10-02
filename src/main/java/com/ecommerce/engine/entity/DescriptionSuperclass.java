package com.ecommerce.engine.entity;

import com.ecommerce.engine.dto.admin.common.MetaDescriptionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class DescriptionSuperclass implements Localable {
    @Id
    @ManyToOne
    Language language;

    String title;

    @Column(columnDefinition = "text")
    String description;

    String metaTitle;
    String metaDescription;

    @Override
    public String getName() {
        return title;
    }

    public DescriptionSuperclass(MetaDescriptionDto metaDescriptionDto) {
        language = new Language(metaDescriptionDto.languageId());
        title = metaDescriptionDto.title();
        description = metaDescriptionDto.description();
        metaTitle = metaDescriptionDto.metaTitle();
        metaDescription = metaDescriptionDto.metaDescription();
    }
}
