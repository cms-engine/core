package com.ecommerce.engine._admin.dto.response;

import com.ecommerce.engine._admin.entity.BackofficeUser;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

public record BackofficeUserResponseDto(Long id, String username, boolean enabled, Set<String> authorities) {

    public BackofficeUserResponseDto(BackofficeUser backofficeUser) {
        this(
                backofficeUser.getId(),
                backofficeUser.getUsername(),
                backofficeUser.isEnabled(),
                backofficeUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
    }
}
