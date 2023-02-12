package com.ecommerce.engine.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "e_user")
public class User implements Updatable<User> {

    @Id
    @Pattern(regexp = "^\\w+$", message = "Username can contain only word character [a-zA-Z0-9_]")
    private String id;

    @NotBlank(message = "Password can't be blank")
    @ToString.Exclude
    private String password;

    @Email(regexp = "^$|^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is wrong")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role can't be null")
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public User updateAllowed(User other) {
        if (!id.equals(other.id))
            return this;

        if (other.role != null)
            role = other.role;
        if (other.email != null)
            email = other.email;
        if (other.password != null && !other.password.equals(password) && !other.password.isBlank())
            password = other.password;

        return this;
    }

}
