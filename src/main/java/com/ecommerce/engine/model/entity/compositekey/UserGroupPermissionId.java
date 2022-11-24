package com.ecommerce.engine.model.entity.compositekey;

import com.ecommerce.engine.model.entity.Permission;
import com.ecommerce.engine.model.entity.UserGroup;
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
