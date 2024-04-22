package com.ecommerce.engine.exception.handler;

import jakarta.validation.Payload;
import java.util.function.Supplier;

public interface ValidationPayload extends Payload, Supplier<String> {}
