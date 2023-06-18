package com.ecommerce.engine.repository;

import com.ecommerce.engine.service.SaveDeleteService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, SaveDeleteService<User> {
}
