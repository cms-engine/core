package com.ecommerce.engine.service;

import com.ecommerce.engine.validation.EntityType;

public interface EntityPresenceService<ID> {

    EntityType getEntityType();

    boolean exists(ID id);
}
