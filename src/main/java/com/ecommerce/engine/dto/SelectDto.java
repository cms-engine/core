package com.ecommerce.engine.dto;

public record SelectDto(String value, String label) {

    public SelectDto(String id) {
        this(id, id);
    }
}
