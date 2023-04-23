package com.ecommerce.engine.service;

import com.ecommerce.engine.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    User save(User user);

    void deleteById(Integer id);

    Optional<User> findById(Integer id);

    Page<User> findAll(PageRequest of);

    void deleteAll(Set<User> selectedItems);

    List<User> findAll();
}
