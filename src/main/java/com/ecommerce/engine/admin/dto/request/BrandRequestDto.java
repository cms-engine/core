package com.ecommerce.engine.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandRequestDto(@NotBlank @Size(max = 255) String name) {}
