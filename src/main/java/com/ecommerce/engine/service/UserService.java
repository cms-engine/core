package com.ecommerce.engine.service;

import com.ecommerce.engine.repository.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveAndFlush(User user);

    void deleteById(String id);

    Optional<User> findById(String id);

    List<User> findAll();

}
