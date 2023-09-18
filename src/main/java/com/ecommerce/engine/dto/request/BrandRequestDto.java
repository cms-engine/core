package com.ecommerce.engine.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BrandRequestDto(@NotBlank String name) {}
