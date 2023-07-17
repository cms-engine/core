package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}
