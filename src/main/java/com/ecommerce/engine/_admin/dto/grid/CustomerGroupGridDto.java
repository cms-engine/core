package com.ecommerce.engine._admin.dto.grid;

import com.ecommerce.engine.entity.CustomerGroup;

public record CustomerGroupGridDto(long id, String name) {

    public CustomerGroupGridDto(CustomerGroup customerGroup) {
        this(customerGroup.getId(), customerGroup.getLocaleName());
    }
}
