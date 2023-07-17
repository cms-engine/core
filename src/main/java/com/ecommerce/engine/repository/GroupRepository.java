package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.Group;
import org.springframework.data.repository.ListCrudRepository;

public interface GroupRepository extends ListCrudRepository<Group, Integer> {
}
