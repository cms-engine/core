package com.ecommerce.engine.service;

import java.util.Optional;

public interface SaveDeleteService<T> {

    T save(T entity);

    void delete(T entity);

    Optional<T> findById(Integer id);
}
