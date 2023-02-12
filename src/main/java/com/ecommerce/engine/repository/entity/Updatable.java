package com.ecommerce.engine.repository.entity;

@FunctionalInterface
public interface Updatable<T> {
    T updateAllowed(T other);
}
