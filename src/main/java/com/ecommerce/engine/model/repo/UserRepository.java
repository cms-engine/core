package com.ecommerce.engine.model.repo;

import com.ecommerce.engine.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
