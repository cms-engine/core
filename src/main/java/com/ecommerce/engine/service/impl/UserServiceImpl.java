package com.ecommerce.engine.service.impl;


import com.ecommerce.engine.repository.UserRepository;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.service.SaveDeleteService;
import com.ecommerce.engine.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService, SaveDeleteService<User> {

    UserRepository userRepository;

    @Override
    public User save(User user) {
        //throw new RuntimeException("Oops, user can't be saved");
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> findAll(PageRequest of) {
        return userRepository.findAll(of);
    }

    @Override
    public void deleteAll(Set<User> selectedItems) {
        userRepository.deleteAll(selectedItems);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
