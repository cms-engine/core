package com.ecommerce.engine.model;

import com.ecommerce.engine.enums.FilterType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record Filter(@NotBlank String field, @NotNull FilterType type, @NotNull @Size(min = 1) Set<String> value) {}
