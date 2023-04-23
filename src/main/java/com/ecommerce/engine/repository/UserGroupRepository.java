package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
