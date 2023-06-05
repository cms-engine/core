package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.CustomerGroupDescriptionId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_customer_group_description")
@IdClass(CustomerGroupDescriptionId.class)
public class CustomerGroupDescription {

    @Id
    @ManyToOne
    @JoinColumn
    private Language language;
    @Id
    @ManyToOne
    @JoinColumn
    private CustomerGroup customerGroup;
    private String name;
    @Lob
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerGroupDescription that = (CustomerGroupDescription) o;
        return language != null && Objects.equals(language, that.language)
                && customerGroup != null && Objects.equals(customerGroup, that.customerGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, customerGroup);
    }
}
