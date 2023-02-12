package com.ecommerce.engine.repository.entity;

import com.ecommerce.engine.repository.entity.compositekey.UserGroupPermissionId;
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
@Table(name = "e_user_group_permission")
@IdClass(UserGroupPermissionId.class)
public class UserGroupPermission {

    @Id
    @ManyToOne
    @JoinColumn
    private UserGroup userGroup;

    @Id
    @Enumerated(value = EnumType.STRING)
    private Permission permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserGroupPermission that = (UserGroupPermission) o;
        return userGroup != null && Objects.equals(userGroup, that.userGroup)
                && permission != null && Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userGroup, permission);
    }

}
