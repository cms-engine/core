package com.ecommerce.engine.model;

@FunctionalInterface
public interface Updatable<T> {
    T updateAllowed(T other);
}
