package com.ecommerce.engine.repository;

import com.ecommerce.engine.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
