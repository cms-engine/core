package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {}
