package com.ecommerce.engine._admin.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    ACTUATOR,
    USERS_WRITE;

    @Override
    public String getAuthority() {
        return name();
    }
}
