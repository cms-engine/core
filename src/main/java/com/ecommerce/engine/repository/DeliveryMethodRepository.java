package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {}
