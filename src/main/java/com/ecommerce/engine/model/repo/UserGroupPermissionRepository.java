package com.ecommerce.engine.model.repo;

import com.ecommerce.engine.model.entity.UserGroup;
import com.ecommerce.engine.model.entity.UserGroupPermission;
import com.ecommerce.engine.model.entity.compositekey.UserGroupPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupPermissionRepository extends JpaRepository<UserGroupPermission, UserGroupPermissionId> {

    List<UserGroupPermission> findAllByUserGroup(UserGroup userGroup);

}
