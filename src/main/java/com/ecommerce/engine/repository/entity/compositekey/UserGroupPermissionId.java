package com.ecommerce.engine.repository.entity.compositekey;

import com.ecommerce.engine.repository.entity.Permission;
import com.ecommerce.engine.repository.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupPermissionId implements Serializable {

    private UserGroup userGroup;
    private Permission permission;

}
