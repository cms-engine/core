package com.ecommerce.engine.exception.handler;

public record ErrorSource(String fieldPointer, Object fieldValue) {}
