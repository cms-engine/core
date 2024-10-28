package com.ecommerce.engine.service;

public interface EntityPresenceService<ID> extends SelectOptionCollector {

    boolean exists(ID id);
}
