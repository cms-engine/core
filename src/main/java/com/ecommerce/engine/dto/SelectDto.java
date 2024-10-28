package com.ecommerce.engine.dto;

public record SelectDto(String value, String label) {

    public SelectDto(String value) {
        this(value, value);
    }
}
