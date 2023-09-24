package com.ecommerce.engine.dto.admin.request;

import jakarta.validation.constraints.Size;

public record CustomerRequestDto(
        Long customerGroupId,
        @Size(max = 255) String firstName,
        @Size(max = 255) String lastName,
        @Size(max = 255) String middleName,
        @Size(max = 255) String phone,
        boolean newsletter,
        boolean enabled) {}
