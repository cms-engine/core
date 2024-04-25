package com.ecommerce.engine.repository;

import com.ecommerce.engine.entity.PurchaseOrder;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {}
