package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderStatusRepository extends JpaRepository<PurchaseOrderStatus, Long> {}
