package com.ecommerce.engine.model;

import com.ecommerce.engine.enums.SortDirection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Sort(@NotBlank String field, @NotNull SortDirection order) {}
