package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.WeightClassDescriptionId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_weight_class_description")
@IdClass(WeightClassDescriptionId.class)
public class WeightClassDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private WeightClass weightClass;
    @Column(length = 32)
    private String title;
    @Column(length = 4)
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WeightClassDescription that = (WeightClassDescription) o;
        return language != null && Objects.equals(language, that.language)
                && weightClass != null && Objects.equals(weightClass, that.weightClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, weightClass);
    }
}
