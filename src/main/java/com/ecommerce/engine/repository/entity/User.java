package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.annotation.VaadinEmail;
import com.ecommerce.engine.annotation.VaadinPassword;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @VaadinPassword
    @ToString.Exclude
    private String password;

    @VaadinEmail
    private String email;

    private Integer age;

    private int timestamp;

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn
    private Group group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User that = (User) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
