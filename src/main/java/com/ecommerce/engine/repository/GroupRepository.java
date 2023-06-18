package com.ecommerce.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GroupRepository extends JpaRepository<Group, Long> {
}
