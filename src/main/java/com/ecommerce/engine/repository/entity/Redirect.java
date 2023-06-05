package com.ecommerce.engine.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

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
