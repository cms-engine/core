package com.ecommerce.engine.exception.handler;

public record ErrorRecord(String title, String fieldPointer, Object fieldValue) {}
