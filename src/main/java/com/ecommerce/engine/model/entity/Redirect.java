package com.ecommerce.engine.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_redirect")
public class Redirect {

    @Id
    private String oldAddress;
    private String newAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Redirect that = (Redirect) o;
        return oldAddress != null && Objects.equals(oldAddress, that.oldAddress);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
