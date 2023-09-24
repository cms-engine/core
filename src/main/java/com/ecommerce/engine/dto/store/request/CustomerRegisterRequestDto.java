package com.ecommerce.engine.dto.store.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRegisterRequestDto(
        @Size(max = 255) String firstName,
        @Size(max = 255) String lastName,
        @Size(max = 255) String middleName,
        @Size(max = 255) String phone,
        @NotBlank @Size(max = 255) String email,
        @NotBlank @Size(max = 255) String password,
        boolean newsletter
) {}
