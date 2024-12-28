package com.ecommerce.engine._admin.dto.request;

import static com.ecommerce.engine._admin.service.BackofficeUserService.PASSWORD_REGEX;

import com.ecommerce.engine._admin.enumeration.Permission;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record BackofficeUserRequestDto(
        @NotNull @Size(max = 255) @Email String username,
        @Pattern(regexp = PASSWORD_REGEX) @NotNull String password,
        Set<@NotNull Permission> authorities) {}
