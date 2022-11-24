package com.ecommerce.engine.model.entity;

import com.ecommerce.engine.model.entity.compositekey.LengthClassDescriptionId;
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
@Table(name = "e_length_class_description")
@IdClass(LengthClassDescriptionId.class)
public class LengthClassDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private LengthClass lengthClass;
    @Column(length = 32)
    private String title;
    @Column(length = 4)
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LengthClassDescription that = (LengthClassDescription) o;
        return language != null && Objects.equals(language, that.language)
                && lengthClass != null && Objects.equals(lengthClass, that.lengthClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, lengthClass);
    }
}
