package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
