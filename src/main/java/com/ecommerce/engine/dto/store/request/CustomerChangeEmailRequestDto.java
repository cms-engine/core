package com.ecommerce.engine.dto.store.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerChangeEmailRequestDto(
        @NotBlank @Size(max = 255) String email
) {}
