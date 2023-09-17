package com.ecommerce.engine.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "customer_group_description")
@IdClass(CustomerGroupDescription.EntityId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerGroupDescription {

    @Id
    Locale locale;

    @Id
    @ManyToOne
    CustomerGroup customerGroup;

    String name;

    @Column(columnDefinition = "text")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerGroupDescription that = (CustomerGroupDescription) o;
        return locale != null
                && Objects.equals(locale, that.locale)
                && customerGroup != null
                && Objects.equals(customerGroup, that.customerGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, customerGroup);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class EntityId implements Serializable {
        Locale locale;
        CustomerGroup customerGroup;
    }
}
