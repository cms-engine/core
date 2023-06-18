package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
