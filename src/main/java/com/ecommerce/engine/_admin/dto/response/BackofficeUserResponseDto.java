package com.ecommerce.engine._admin.dto.response;

import com.ecommerce.engine._admin.entity.BackofficeUser;
import com.ecommerce.engine._admin.enumeration.Permission;
import java.util.Set;

public record BackofficeUserResponseDto(Long id, String username, boolean enabled, Set<Permission> authorities) {

    public BackofficeUserResponseDto(BackofficeUser backofficeUser) {
        this(
                backofficeUser.getId(),
                backofficeUser.getUsername(),
                backofficeUser.isEnabled(),
                backofficeUser.getAuthorities());
    }
}
