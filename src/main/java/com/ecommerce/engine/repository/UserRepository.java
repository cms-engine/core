package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
