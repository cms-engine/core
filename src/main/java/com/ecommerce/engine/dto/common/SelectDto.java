package com.ecommerce.engine.dto.common;

public record SelectDto(String id, String label) {

    public SelectDto(String id) {
        this(id, id);
    }
}
