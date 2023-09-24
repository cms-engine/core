package com.ecommerce.engine.dto.store.request;

import jakarta.validation.constraints.Size;

public record CustomerInfoRequestDto(
        @Size(max = 255) String firstName,
        @Size(max = 255) String lastName,
        @Size(max = 255) String middleName,
        @Size(max = 255) String phone,
        boolean newsletter) {}
