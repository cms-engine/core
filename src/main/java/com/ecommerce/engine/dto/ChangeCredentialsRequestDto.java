package com.ecommerce.engine.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangeCredentialsRequestDto(
        @Size(max = 255) @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email,
        @Size(max = 255) String password) {}
